package com.zarembin.epamjsp.tagcustom;

import com.zarembin.epamjsp.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class HelloUserTag extends TagSupport {
    private User user;
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public int doStartTag() throws JspException {
        String locale = (String) pageContext.getSession().getAttribute("changeLanguage");
        Locale current = ("en_US".equals(locale)) ? new Locale("en", "US") : new Locale("ru", "RU");
        ResourceBundle rb = ResourceBundle.getBundle("resource.pagecontent", current);

        StringBuffer stringBufferResult = new StringBuffer();
        if(user != null) {
            try {
                stringBufferResult.append("<b>").append(user.getName()).append(" ").append(user.getLastname()).append("</b>").append("<br>");
                if (user.isAdmin()) {
                    stringBufferResult.append("<i>").append(rb.getString("label.userRoleAdmin")).append("</i>");
                } else {
                    stringBufferResult.append("<i>").append(rb.getString("label.userRoleUser")).append("</i>");
                }
                pageContext.getOut().write("<hr/>" + stringBufferResult.toString() + "<hr/>");
            } catch (IOException e) {
                throw new JspException(e.getMessage());
            }
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;   /// для дальнейшей обработки страницы
    }
}