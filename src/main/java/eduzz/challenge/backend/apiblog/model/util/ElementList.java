package eduzz.challenge.backend.apiblog.model.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Returns a element list of the desired type
 * @author Michell Algarra Barros
 * @version 1.1
 * @since 1.1
 */
public class ElementList<T> {

	/**
	 * Formats the search result from the database into "data"...data category...obtained data
	 * @param arrayCategory Category that is shown in the element search
	 * @param iterable All elements caught in database request
	 * @return Map - Formated data 
	 */
	public Map<String, Map<String, List<T>>> getList(String arrayCategory, Iterable<T> iterable) {
		Map<String, Map<String, List<T>>> data = new HashMap<>();
		Map<String, List<T>> categoryMap = new HashMap<>();
		List<T> list = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
		
		categoryMap.put(arrayCategory, list);
		data.put("data", categoryMap);
		
		return data;
	}
}
