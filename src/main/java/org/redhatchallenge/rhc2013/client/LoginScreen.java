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
import org.redhatchallenge.rhc2013.shared.FieldVerifier;

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

    @UiField Label loginEmailLabel;
    @UiField Label loginPasswordLabel;

    private AuthenticationServiceAsync authenticationService = null;

    public LoginScreen() {
        Resources.INSTANCE.grid().ensureInjected();
        Resources.INSTANCE.main().ensureInjected();
        initWidget(UiBinder.createAndBindUi(this));

        loginButton.getElement().getStyle().setCursor(Style.Cursor.POINTER);

        if(LocaleInfo.getCurrentLocale().getLocaleName().equals("ch")) {
            socialButton1.setHref("http://page.renren.com/601220914?checked=true");
            socialButton2.setHref("http://e.weibo.com/redhatchina");
        }
        else {
            socialButton1.setHref("https://www.facebook.com/redhatinc?fref=ts");
            socialButton2.setHref("https://twitter.com/red_hat_apac");
        }

    }

    @UiHandler("loginButton")
    public void handleLoginButtonClick(ClickEvent event) {

        int successCounter = 0;

        if(FieldVerifier.emailIsNull(emailField.getText())){
            loginEmailLabel.setText(messages.emailEmpty());
        }
            else if(!FieldVerifier.isValidEmail(emailField.getText())){
                loginEmailLabel.setText(messages.invalidEmailFormat());
            }
                else{
                    loginEmailLabel.setText("");
                    successCounter++;
                }

        if(FieldVerifier.passwordIsNull(passwordField.getText())){
            loginPasswordLabel.setText(messages.emptyPassword());
        }
            else{
                loginPasswordLabel.setText("");
                successCounter++;
            }
        if(successCounter == 2){
            authenticateStudent();
        }
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