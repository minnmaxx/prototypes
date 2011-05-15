package reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.util.ReflectionUtils;

@Prune
public class Child  {

	private Discard childDiscard = new Discard();
	
	public static List<Field> getAllFields( Class<?> clazz ) {
		
		if( clazz == Object.class ) {
			return new ArrayList<Field>();
		}
		
		List<Field> fields = getAllFields( clazz.getSuperclass() );
		
		fields.addAll( Arrays.asList( clazz.getDeclaredFields() ) );
		
		return fields;
	}
	
	public static void printAllFields( Object object ) {
		
		List<Field> fields = getAllFields( object.getClass() );
		
		for( int i = 0; i < fields.size(); i++ ) {
			Field field = fields.get(i);
			ReflectionUtils.makeAccessible(field);
			Object fieldValue = ReflectionUtils.getField(field,object);
			System.out.println( field.getName() + (fieldValue == null ? " null" : " not null") );
		}
	}
	
	public static<T> T prune( Object parent, T child ) {
		
		if( child.getClass().getAnnotation(Prune.class) != null ) {
		
			List<Field> fields = getAllFields( Child.class );
			for( int i = 0; i < fields.size(); i++ ) {
				
				Field field = fields.get(i);
				ReflectionUtils.makeAccessible(field);
				Object value = ReflectionUtils.getField(field,child);
				
				if( value != parent ) {
					
					if( Discard.class.isAssignableFrom(field.getType()) ) 
					{
						ReflectionUtils.setField(field,child,null);
					} 
					else if( DiscardCollection.class.isAssignableFrom( field.getType() ) )
					{
						ReflectionUtils.setField(field,child,null);
					} 
					else if (Collection.class.isAssignableFrom(field.getType())) 
					{
						Collection<?> collection = (Collection<?>) value;
						for( Object item : collection ) {
							prune( child, item );
						}
					} else {
						ReflectionUtils.setField(field,child,prune(child,value));
					}
				}
			}
		}
		
		return child;
	}
	
	public static<T> Collection<T> pruneCollection( Object parent, Collection<T> children ) {
	
		return children;
	}
	
	public static void main(String[] args) {
		
//		Field[] fields = Child.class.getDeclaredFields();
//		
//		for( int i = 0; i < fields.length; i++ ) {
//			System.out.println( fields[i].getName() );
//		}
		
//		List<Field> fields = getAllFields( Child.class );	
//		for( int i = 0; i < fields.size(); i++ ) {
//			System.out.println( fields.get(i).getName() );
//		}
//		
//		Class<?> clazz = Child.class;
//		Prune prune = clazz.getAnnotation( Prune.class );
//		
//		System.out.println( prune != null );
		
		Parent parent = new Parent();
		Child child = new Child();
		
		
		Object object = child;
		printAllFields(object);
		
		object = prune(null,object);
		printAllFields(object);
	}
}
