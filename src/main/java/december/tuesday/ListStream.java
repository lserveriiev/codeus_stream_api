package december.tuesday;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@UtilityClass
public class ListStream {

    public List<String> reverseSort(List<String>... words) {
        return Arrays.stream(words)
                .flatMap(Collection::stream)
                .sorted(Comparator.reverseOrder())
                .toList();
    }

}
