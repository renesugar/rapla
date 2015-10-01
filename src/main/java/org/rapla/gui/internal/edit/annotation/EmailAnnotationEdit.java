package org.rapla.gui.internal.edit.annotation;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import org.rapla.client.extensionpoints.AnnotationEdit;
import org.rapla.client.extensionpoints.AnnotationEditAttributeExtension;
import org.rapla.entities.Annotatable;
import org.rapla.entities.dynamictype.Attribute;
import org.rapla.entities.dynamictype.AttributeAnnotations;
import org.rapla.entities.dynamictype.AttributeType;
import org.rapla.framework.RaplaContext;
import org.rapla.framework.RaplaException;
import org.rapla.gui.EditField;
import org.rapla.gui.RaplaGUIComponent;
import org.rapla.gui.internal.edit.fields.BooleanField;
import org.rapla.inject.Extension;

@Extension(provides= AnnotationEditAttributeExtension.class, id="email")
public class EmailAnnotationEdit extends RaplaGUIComponent implements AnnotationEdit
{

    private final String annotationName = AttributeAnnotations.KEY_EMAIL;

    @Inject
    public EmailAnnotationEdit(RaplaContext context) {
        super(context);
    }

    @Override
    public Collection<? extends EditField> createEditField(Annotatable annotatable) {
        if (!( annotatable instanceof Attribute))
        {
            return Collections.emptyList();
        }
        Attribute attribute = (Attribute)annotatable;
        AttributeType type = attribute.getType();
        if (type!= AttributeType.STRING)
        {
            return Collections.emptyList();
        }
        String annotation = annotatable.getAnnotation(annotationName);
        BooleanField field = new BooleanField(getContext(),getString(annotationName));
        if ( annotation != null )
        {
            field.setValue( annotation.equalsIgnoreCase("true"));
        }
        else
        {
            if ( attribute.getKey().equalsIgnoreCase(annotationName))
            {
                field.setValue( true );
            }
        }
        return Collections.singleton(field);
    }

    @Override
    public void mapTo(EditField field, Annotatable annotatable) throws RaplaException 
    {
        if ( field != null)
        {
            Boolean value = ((BooleanField)field).getValue();
            if ( value != null )
            {
                if ( value )
                {
                    annotatable.setAnnotation(annotationName, Boolean.TRUE.toString());
                    return;
                }
                else if (( (Attribute)annotatable).getKey().equals(annotationName))
                {
                    annotatable.setAnnotation(annotationName, Boolean.FALSE.toString());
                }
            }
        }
        annotatable.setAnnotation(annotationName, null);
    }

}
