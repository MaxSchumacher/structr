package org.structr.web.property;

import org.structr.common.SecurityContext;
import org.structr.common.property.Property;
import org.structr.core.GraphObject;
import org.structr.core.converter.PropertyConverter;
import org.structr.web.converter.DynamicConverter;

/**
 *
 * @author Christian Morgner
 */
public class DynamicContentProperty extends Property<String> {
	
	public DynamicContentProperty(String name) {
		super(name);
	}
	
	@Override
	public PropertyConverter<String, ?> databaseConverter(SecurityContext securityContext, GraphObject entity) {
		return new DynamicConverter(securityContext, entity);
	}

	@Override
	public PropertyConverter<?, String> inputConverter(SecurityContext securityContext) {
		return null;
	}
}
