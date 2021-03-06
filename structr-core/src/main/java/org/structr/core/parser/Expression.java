/**
 * Copyright (C) 2010-2014 Morgner UG (haftungsbeschränkt)
 *
 * This file is part of Structr <http://structr.org>.
 *
 * Structr is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Structr is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Structr.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.structr.core.parser;

import java.util.LinkedList;
import java.util.List;
import org.structr.common.SecurityContext;
import org.structr.common.error.FrameworkException;
import org.structr.core.GraphObject;
import org.structr.schema.action.ActionContext;

/**
 *
 * @author Christian Morgner
 */
public abstract class Expression {

	protected List<Expression> expressions = new LinkedList<>();
	protected Expression parent            = null;
	protected String name                  = null;
	protected int level                    = 0;

	public Expression() {
		this(null);
	}

	public Expression(final String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public void add(final Expression expression) throws FrameworkException {

		expression.parent = this;
		expression.level  = this.level + 1;

		this.expressions.add(expression);
	}

	public Expression getParent() {
		return parent;
	}

	public abstract Object evaluate(final SecurityContext securityContext, final ActionContext ctx, final GraphObject entity) throws FrameworkException;
}
