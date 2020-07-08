package eduzz.challenge.backend.apiblog.model.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Returns a element of the desired type by it's id
 * @author Michell Algarra Barros
 * @version 1.1
 * @since 1.1
 */
public class ElementById<T> {

	/**
	 * Formats the search result from the database into "data"...data category...obtained data
	 * @param arrayCategory Category that is shown in the element search
	 * @param optional Element caught in database search by id
	 * @return Map - Formated data 
	 */
	public Map<String, Map<String, List<T>>> getElement(String arrayCategory, Optional<T> optional) {
		Map<String, Map<String, List<T>>> data = new HashMap<>();
		Map<String, List<T>> categoryMap = new HashMap<>();
		List<T> list = optional.stream().collect(Collectors.toList());
		
		categoryMap.put(arrayCategory, list);
		data.put("data", categoryMap);
		
		return data;
	}
}
