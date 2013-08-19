package org.redhatchallenge.rhc2013.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;
import org.redhatchallenge.rhc2013.resources.Resources;

/**
 * @author  Terry Chia (terrycwk1994@gmail.com)
 */
public class IndexScreen extends Composite {
    interface IndexScreenUiBinder extends UiBinder<Widget, IndexScreen> {
    }

    private static IndexScreenUiBinder UiBinder = GWT.create(IndexScreenUiBinder.class);
    private MessageMessages messages = GWT.create(MessageMessages.class);
    @UiField Image registerImage;
    @UiField HTML challengeLink;
    @UiField Anchor socialButton1;
    @UiField Anchor socialButton2;

    public IndexScreen() {

        ScriptInjector.fromUrl("js/jquery-1.7.1.min.js").inject();

        Resources.INSTANCE.main().ensureInjected();
        Resources.INSTANCE.grid().ensureInjected();
        Resources.INSTANCE.buttons().ensureInjected();
        Resources.INSTANCE.assetStyles().ensureInjected();

        initWidget(UiBinder.createAndBindUi(this));

        // Sets cursor to indicate the image is clickable
        registerImage.getElement().getStyle().setCursor(Style.Cursor.POINTER);

        challengeLink.setHTML("<h1><font color=\"#CC0000\">" + messages.takeChallenge() + "!</font></h1>");
        challengeLink.getElement().getStyle().setCursor(Style.Cursor.POINTER);

        if(LocaleInfo.getCurrentLocale().getLocaleName().equals("ch")) {
            socialButton1.setHref("http://page.renren.com/601220914?checked=true");
            socialButton2.setHref("http://e.weibo.com/redhatchina");
        }
        else {
            socialButton1.setHref("https://www.facebook.com/redhatinc?fref=ts");
            socialButton2.setHref("https://twitter.com/red_hat_apac");
        }

    }

    @UiHandler({"registerImage", "challengeLink"})
    public void handleClick(ClickEvent event) {
        History.newItem("registration", true);
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        Jquery.countdown();
        if(LocaleInfo.getCurrentLocale().getLocaleName().equals("en")) {
            Jquery.bindEn(5 * 24 * 60 * 60 * 1000);
        }

        else if(LocaleInfo.getCurrentLocale().getLocaleName().equals("ch")) {
            Jquery.bindCh(5*24*60*60*1000);
        }
        else if(LocaleInfo.getCurrentLocale().getLocaleName().equals("zh")) {
            Jquery.bindCh(5*24*60*60*1000);
        }
        Jquery.prettyPhoto();
    }
}