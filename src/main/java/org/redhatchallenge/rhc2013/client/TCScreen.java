package org.redhatchallenge.rhc2013.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author: Terry Chia (terrycwk1994@gmail.com)
 */
public class TCScreen extends Composite {
    interface TCScreenUiBinder extends UiBinder<Widget, TCScreen> {
    }

    private static TCScreenUiBinder UiBinder = GWT.create(TCScreenUiBinder.class);

    @UiField Image registerImage;
    @UiField Anchor socialButton1;
    @UiField Anchor socialButton2;

    public TCScreen() {
        initWidget(UiBinder.createAndBindUi(this));

        registerImage.getElement().getStyle().setCursor(Style.Cursor.POINTER);

        if(LocaleInfo.getCurrentLocale().getLocaleName().equals("ch")) {
            socialButton1.setHref("http://page.renren.com/601220914?checked=true");
            socialButton2.setHref("http://e.weibo.com/redhatchina");
        }
        else {
            socialButton1.setHref("https://www.facebook.com/redhatinc?fref=ts");
            socialButton2.setHref("https://twitter.com/red_hat_apac");
        }
    }

    @UiHandler({"registerImage"})
    public void handleClick(ClickEvent event) {
        History.newItem("registration", true);
    }
}