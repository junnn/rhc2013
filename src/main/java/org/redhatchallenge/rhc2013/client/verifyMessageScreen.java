package org.redhatchallenge.rhc2013.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import org.redhatchallenge.rhc2013.resources.Resources;

/**
 * @author: Terry Chia (terrycwk1994@gmail.com)
 */
public class verifyMessageScreen extends Composite {
    interface MessageScreenUiBinder extends UiBinder<Widget, verifyMessageScreen> {
    }

    private static MessageScreenUiBinder UiBinder = GWT.create(MessageScreenUiBinder.class);

    @UiField HTML messageLabel;
    @UiField Image socialButton1;
//    @UiField Image socialButton2;

    public verifyMessageScreen(String message) {
        initWidget(UiBinder.createAndBindUi(this));
        messageLabel.setHTML(message);

        Resources.INSTANCE.grid().ensureInjected();
        Resources.INSTANCE.main().ensureInjected();

        socialButton1.setResource(Resources.INSTANCE.socialButton1());
        socialButton1.getElement().getStyle().setCursor(Style.Cursor.POINTER);
    }

    @UiHandler("socialButton1")
    public void handleSocialButton1Click(ClickEvent event) {
        Jquery.facebookShare();
    }
}