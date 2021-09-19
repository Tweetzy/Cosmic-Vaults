import ca.tweetzy.tweety.MathUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The current file has been created by Kiran Hart
 * Date Created: September 15 2021
 * Time Created: 10:10 p.m.
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise
 */
public final class Testing {

	public static void main(String[] args) {
		List<String> range = IntStream.rangeClosed(1, 1000000).mapToObj(Integer::toString).collect(Collectors.toList());

		System.out.println(String.join("|", range));
	}
}
