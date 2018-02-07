package com.zarembin.epamjsp.tagcustom;


import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


@SuppressWarnings("serial")
public class InfoTimeTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        DateFormat dateFormat;
        if("en_US".equals(pageContext.getSession().getAttribute("changeLanguage")))
            dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM , Locale.US);
        else{
            dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("ru","RU"));
        }
        String time = "<b> " + dateFormat.format(new Date()) + " </b>";
        try {
            JspWriter out = pageContext.getOut();
            out.write(time);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;  //  если отсутствует тело
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;   /// для дальнейшей обработки страницы
    }
}