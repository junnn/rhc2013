package org.redhatchallenge.rhc2013.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.redhatchallenge.rhc2013.resources.Resources;

/**
 * @author: Terry Chia (terrycwk1994@gmail.com)
 */
public class LoginScreen extends Composite {
    interface LoginScreenUiBinder extends UiBinder<Widget, LoginScreen> {
    }

    private static LoginScreenUiBinder UiBinder = GWT.create(LoginScreenUiBinder.class);
    private MessageMessages messages = GWT.create(MessageMessages.class);

    @UiField TextBox emailField;
    @UiField PasswordTextBox passwordField;
    @UiField Image loginButton;
    @UiField CheckBox rememberMeField;
    @UiField Hyperlink resetPasswordLink;
    @UiField Label errorLabel;
    @UiField Anchor socialButton1;
    @UiField Anchor socialButton2;

    private AuthenticationServiceAsync authenticationService = null;

    public LoginScreen() {
        Resources.INSTANCE.grid().ensureInjected();
        Resources.INSTANCE.main().ensureInjected();
        initWidget(UiBinder.createAndBindUi(this));

        loginButton.getElement().getStyle().setCursor(Style.Cursor.POINTER);

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

    }

    @UiHandler("loginButton")
    public void handleLoginButtonClick(ClickEvent event) {
        authenticateStudent();
    }

    @UiHandler({"emailField", "passwordField", "rememberMeField"})
    public void handleKeyUp(KeyUpEvent event) {
        if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            authenticateStudent();
        }
    }

    @UiHandler("resetPasswordLink")
    public void handleResetPasswordLinkClick(ClickEvent event) {
        ContentContainer.INSTANCE.setContent(new TriggerPasswordResetScreen());
    }

    private void authenticateStudent() {

        final String email = emailField.getText();
        final String password = passwordField.getText();
        final Boolean rememberMe = rememberMeField.getValue();

        authenticationService = AuthenticationService.Util.getInstance();

        authenticationService.authenticateStudent(email, password, rememberMe, new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable throwable) {
                errorLabel.setText(messages.unexpectedError());
            }

            @Override
            public void onSuccess(Boolean bool) {
                if(bool) {
                    /**
                     * Clears the local storage on a fresh login to prevent the
                     * data of an old user from being populated.
                     */
                    Storage localStorage = Storage.getLocalStorageIfSupported();
                    localStorage.clear();

                    ContentContainer.INSTANCE.setContent(new ContestDetailsScreen());
                    RootPanel.get("header").clear();
                    RootPanel.get("header").add(new AuthenticatedHeader());
                }

                else {
                    errorLabel.setText(messages.loginUnsuccessful());
                }

            }
        });
    }
}