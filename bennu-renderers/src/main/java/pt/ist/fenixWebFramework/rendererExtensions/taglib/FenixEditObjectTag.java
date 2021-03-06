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
package pt.ist.fenixWebFramework.rendererExtensions.taglib;

import javax.servlet.jsp.JspException;

import pt.ist.fenixWebFramework.renderers.taglib.EditObjectTag;
import pt.ist.fenixframework.FenixFramework;

public class FenixEditObjectTag extends EditObjectTag {
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Override
    protected Object getTargetObject() throws JspException {
        Object object = super.getTargetObject();

        if (object == null) {
            object = getPersistentObject();

            if (object != null) {
                return super.getTargetObjectByProperty(object);
            }
        }

        return object;
    }

    protected Object getPersistentObject() throws JspException {
        if (getOid() != null) {
            return FenixFramework.getDomainObject(getOid());
        }

        return null;
    }

    @Override
    public void release() {
        super.release();
        this.oid = null;
    }

}
