package org.redhatchallenge.rhc2013.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.redhatchallenge.rhc2013.resources.Resources;

/**
 * @author: Terry Chia (terrycwk1994@gmail.com)
 */
public class TriggerPasswordResetScreen extends Composite {

    interface ResetPasswordScreenUiBinder extends UiBinder<Widget, TriggerPasswordResetScreen> {
    }

    private static ResetPasswordScreenUiBinder UiBinder = GWT.create(ResetPasswordScreenUiBinder.class);
    private static MessageMessages messages = GWT.create(MessageMessages.class);

    @UiField TextBox emailField;
    @UiField TextBox contactField;
    @UiField Image resetPasswordButton;
    @UiField Label errorLabel;
    @UiField Anchor socialButton1;
    @UiField Anchor socialButton2;

    private AuthenticationServiceAsync authenticationService = null;

    public TriggerPasswordResetScreen() {
        initWidget(UiBinder.createAndBindUi(this));

        if(LocaleInfo.getCurrentLocale().getLocaleName().equals("ch")) {
            socialButton1.setVisible(false);
            socialButton2.setTarget("_blank");
            socialButton2.setHref("http://e.weibo.com/redhatchina");
        }
        else {
            socialButton1.setTarget("_blank");
            socialButton1.setHref("https://www.facebook.com/redhatinc?fref=ts");
            socialButton2.setTarget("_blank");
            socialButton2.setHref("https://twitter.com/red_hat_apac");
        }

        resetPasswordButton.getElement().getStyle().setCursor(Style.Cursor.POINTER);
    }

    @UiHandler("resetPasswordButton")
    public void handleResetPasswordButtonClick(ClickEvent event) {
        resetPassword();
    }

    @UiHandler({"emailField", "contactField"})
    public void handleKeyUp(KeyUpEvent event) {
        if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            resetPassword();
        }
    }

    private void resetPassword() {

        resetPasswordButton.setResource(Resources.INSTANCE.submitButtonGrey());

        final String email = emailField.getText();
        final String contact = contactField.getText();

        authenticationService = AuthenticationService.Util.getInstance();

        authenticationService.triggerResetPassword(email, contact, new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                errorLabel.setText(messages.unexpectedError());
                resetPasswordButton.setResource(Resources.INSTANCE.submitButton());
            }

            @Override
            public void onSuccess(Boolean result) {
                if(result) {
                    ContentContainer.INSTANCE.setContent(new MessageScreen(messages.passwordResetInstruction()));
                }

                else {
                    errorLabel.setText(messages.passwordResetError());
                    resetPasswordButton.setResource(Resources.INSTANCE.submitButton());
                }
            }
        });
    }
}