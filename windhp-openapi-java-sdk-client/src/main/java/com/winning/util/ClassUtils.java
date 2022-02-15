package com.winning.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author xch
 * @date 2022/1/7 16:11
 */
public class ClassUtils {

	private ClassUtils() {}

	public static Map<String, String> objectToMap(Object obj){    
		Map<String, String> map = new HashMap<>(32);
        List<Field> fields=ClassUtils.getFieldList(obj.getClass());
        for (Field field : fields) {
        		int mod = field.getModifiers();  
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                continue;    
            }    
            field.setAccessible(true);
            try {
            	Object value=field.get(obj);
                if(value!=null) {
                		map.put(field.getName(), value.toString()); 
                }
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(),e);
			}
		}
        return map;  
    }

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getFieldList(Class<?> clazz) {
		List<Field> fields = new ArrayList<>();
		Set<String> filedNames = new HashSet<>();
		for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
			try {
				Field[] list = c.getDeclaredFields();
				for (Field field : list) {
					String name = field.getName();
					if (filedNames.contains(name)) {
						continue;
					}
					filedNames.add(field.getName());
					fields.add(field);
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(),e);
			}
		}
		return fields;
	}
}
