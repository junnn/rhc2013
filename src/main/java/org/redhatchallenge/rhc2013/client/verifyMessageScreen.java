package org.redhatchallenge.rhc2013.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
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
    @UiField HTML socialButton2;

    public verifyMessageScreen(String message) {
        initWidget(UiBinder.createAndBindUi(this));
        messageLabel.setHTML(message);

        Resources.INSTANCE.grid().ensureInjected();
        Resources.INSTANCE.main().ensureInjected();

        socialButton1.getElement().getStyle().setCursor(Style.Cursor.POINTER);
        socialButton2.getElement().getStyle().setCursor(Style.Cursor.POINTER);

        if(LocaleInfo.getCurrentLocale().getLocaleName().equals("en")) {
            ClickHandler handlerEn = new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Jquery.facebookShareEn();
                }
            };

            socialButton1.setResource(Resources.INSTANCE.socialButton1());
            socialButton1.addClickHandler(handlerEn);
            socialButton2.setHTML("<a href=\"https://twitter.com/share\" class=\"twitter-share-button\" data-count=\"none\" data-text=\"Join Red Hat Challenge 2013 now!\" data-url=\"https://127.0.0.1\" data-lang=\"en\">Tweet</a>\n");
        }

        else if(LocaleInfo.getCurrentLocale().getLocaleName().equals("ch")) {
            ClickHandler handlerCh = new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Window.open("http://share.renren.com/share/buttonshare?link=http://redhatchallenge2013-rhc2013.rhcloud.com/", "renren-share-dialog", "width=626,height=436");
                }
            };

            socialButton2.setHTML("<img src=\"images/socialButton1_ch.png\"/>\n");
            socialButton2.addClickHandler(handlerCh);
        }

        else if(LocaleInfo.getCurrentLocale().getLocaleName().equals("zh")) {
            ClickHandler handlerZh = new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Jquery.facebookShareZh();
                }
            };

            socialButton1.setResource(Resources.INSTANCE.socialButton1());
            socialButton1.addClickHandler(handlerZh);
            socialButton2.setHTML("<a href=\"https://twitter.com/share\" class=\"twitter-share-button\" target=\"_blank\" data-count=\"none\" data-text=\"Join Red Hat Challenge 2013 now! -Chinese version-\" data-url=\"https://127.0.0.1\" data-lang=\"en\">Tweet</a>\n");
        }
    }
}