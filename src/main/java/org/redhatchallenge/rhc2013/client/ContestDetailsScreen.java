package org.redhatchallenge.rhc2013.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageMap;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.redhatchallenge.rhc2013.shared.Student;

/**
 * @author: Terry Chia (terrycwk1994@gmail.com)
 */
public class ContestDetailsScreen extends Composite {
    interface ContestDetailsScreenUiBinder extends UiBinder<Widget, ContestDetailsScreen> {
    }

    private static ContestDetailsScreenUiBinder UiBinder = GWT.create(ContestDetailsScreenUiBinder.class);

    private ProfileServiceAsync profileService = null;
    private MessageMessages messages = GWT.create(MessageMessages.class);

    @UiField HTML welcomeLabel;
    @UiField TextBox emailField;
    @UiField TextBox timeSlotField;
    @UiField TextBox languageField;
    public ContestDetailsScreen() {

        initWidget(UiBinder.createAndBindUi(this));

        emailField.setReadOnly(true);
        timeSlotField.setReadOnly(true);
        languageField.setReadOnly(true);

        /**
         * If HTML5 storage does not contain the profile data, retrieves the data
         * from the server through a RPC call. Else retrieves first name from the
         * local storage.
         */
        final Storage localStorage = Storage.getLocalStorageIfSupported();
        StorageMap localStorageMap = new StorageMap(localStorage);
        if(localStorageMap.size() != 11) {
            profileService = ProfileService.Util.getInstance();
            profileService.getProfileData(new AsyncCallback<Student>() {
                @Override
                public void onFailure(Throwable caught) {
                    ContentContainer.INSTANCE.setContent(new MessageScreen(messages.probablyNotLoginIn()));
                }

                @Override
                public void onSuccess(Student result) {
                    if(result == null) {
                        ContentContainer.INSTANCE.setContent(new MessageScreen("<h1>"+ messages.loginError() +"?</h1>"));
                    }

                    else {
                        welcomeLabel.setHTML("<FONT SIZE=6>"+ messages.hello() + ", "+ result.getFirstName() +"</FONT>");
                        /**
                         * If browser supports HTML5 storage, stores the authenticated user's
                         * profile data.
                         */
                        if(localStorage != null) {
                            localStorage.setItem("email", result.getEmail());
                            localStorage.setItem("firstName", result.getFirstName());
                            localStorage.setItem("lastName", result.getLastName());
                            localStorage.setItem("contact", result.getContact());
                            localStorage.setItem("country", result.getCountry());
                            localStorage.setItem("countryCode", result.getCountryCode());
                            localStorage.setItem("school", result.getSchool());
                            localStorage.setItem("lecturerFirstName", result.getLecturerFirstName());
                            localStorage.setItem("lecturerLastName", result.getLecturerLastName());
                            localStorage.setItem("lecturerEmail", result.getLecturerEmail());
                            localStorage.setItem("language", result.getLanguage());
                            localStorage.setItem("timeSlot", Long.toString(result.getTimeslot()));

                            emailField.setText(result.getEmail());
                            languageField.setText(result.getLanguage());
                            timeSlotField.setText(Long.toString(result.getTimeslot()));

                        }
                    }
                }
            });
        }

        else {
            welcomeLabel.setHTML("<FONT SIZE=6>"+ messages.hello() + ", "+ localStorage.getItem("firstName")  +"</FONT>");
            emailField.setText(localStorage.getItem("email"));
            languageField.setText(localStorage.getItem("language"));
            timeSlotField.setText(localStorage.getItem("timeSlot"));
        }
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Jquery.countdown();
        if(LocaleInfo.getCurrentLocale().getLocaleName().equals("en")) {
            Jquery.bindEn(5*24*60*60*1000);
        }

        else if(LocaleInfo.getCurrentLocale().getLocaleName().equals("ch")) {
            Jquery.bindCh(5*24*60*60*1000);
        }
        else if(LocaleInfo.getCurrentLocale().getLocaleName().equals("zh")) {
            Jquery.bindCh(5*24*60*60*1000);
        }
    }
}