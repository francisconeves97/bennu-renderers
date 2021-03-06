/**
 * Copyright © 2008 Instituto Superior Técnico
 *
 * This file is part of Bennu Renderers Framework.
 *
 * Bennu Renderers Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bennu Renderers Framework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Bennu Renderers Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.fenixWebFramework.renderers.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditLinkTag extends TagSupport {

    private static final Logger logger = LoggerFactory.getLogger(EditLinkTag.class);

    private String name;

    private String path;

    private String module;

    private String redirect;

    public EditLinkTag() {
        super();
    }

    @Override
    public void release() {
        super.release();

        this.name = null;
        this.path = null;
        this.module = null;
        this.redirect = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    @Override
    public int doStartTag() throws JspException {
        BaseRenderObjectTag tag = (BaseRenderObjectTag) findAncestorWithClass(this, BaseRenderObjectTag.class);

        if (tag == null) {
            logger.warn("Destination {} specified but no parent tag supports destinations", getName());
        } else {
            setDestination(tag, getPath(), getModule(), redirectToBoolean(getRedirect()));
        }

        return SKIP_BODY;
    }

    protected void setDestination(BaseRenderObjectTag tag, String path, String module, boolean redirect) {
        tag.addDestination(name, path, module, redirect);
    }

    protected boolean redirectToBoolean(String redirect) {
        if (redirect != null && (redirect.equalsIgnoreCase("true") || redirect.equalsIgnoreCase("yes"))) {
            return true;
        }

        return false;
    }
}
