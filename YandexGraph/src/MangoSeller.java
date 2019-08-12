import java.util.*;

/**
 * @author vasvaller
 * Unit: Graphs
 * Lesson: BFS (breadth-first search)
 * Task: find any shortest way from Alice to MangoSeller
 */
public class MangoSeller {
    static String shortestWay = "no way to MangoSeller :(";
    static int steps = -1;
    static HashMap<String, Person> list = new HashMap<>();

    public static void main(String[] args) {
        ArrayList<String> condition = new ArrayList<>();
        condition.addAll(Arrays.asList(
                "Alice, Bill, Clint, Dan, Gray, Jerry", // first name is node, else names are friends of this node
                "Bill, Alice, Clint",
                "Clint, Alice, Bill, Dan",
                "Dan, Alice, Clint, Fox",
                "Gray, Alice, Jerry",
                "Fox, Dan, MangoSeller",
                "Jerry, Alice, Gray, Eric",
                "Eric, Jerry, MangoSeller",
                "MangoSeller, Fox, Eric"
        ));

        searchShortestWay(condition, "Alice", "MangoSeller");
        printSteps();
        System.out.println(shortestWay.trim());
    }

    /**
     * just print count of steps
     */
    public static void printSteps() {
        String template = "count of steps: ";
        if (steps == -1) System.out.println(template + "∞");
        else System.out.println(template + steps);
    }

    /**
     * start this method to find shortest way
     * @param condition list of persons and their friends. First name is person, other names are friends of this person
     * @param from the person with whom the search for the path begins
     * @param to the person the way to which we are looking for
     */
    static String searchShortestWay(ArrayList<String> condition, String from, String to) {
        fillPersonsList(condition);
        findBestWay(from, to);
        return shortestWay.trim();
    }

    /**
     * all util logic
     * @param fromName the person with whom the search for the path begins
     * @param targetName the person the way to which we are looking for
     */
    static void findBestWay(String fromName, String targetName) {
        LinkedList<String> queue = new LinkedList<>();
        Person rootPerson = list.get(fromName);
        Person targetPerson = list.get(targetName);
        queue.add(rootPerson.name);
        ArrayList<Person> checkedPersons = new ArrayList<>();

        while (!queue.isEmpty()) {
            String currentPath = queue.remove(0);
            String[] pathNames = currentPath.split(" -> ");
            int potencialMinimumSteps = pathNames.length;
            Person currentPerson = list.get(pathNames[pathNames.length - 1]);

            // is currentPerson is targetPerson
            if (!checkedPersons.contains(currentPerson) && currentPerson.equals(targetPerson)) {
                shortestWay = currentPath;
                steps = potencialMinimumSteps;
                return;
            } else if (checkedPersons.contains(currentPerson)) continue;
            else checkedPersons.add(currentPerson);

            // add all current person's friends
            if (!checkedPersons.containsAll(currentPerson.friends)) {
                currentPerson.friends.stream()
                        .filter(friendOfCurrentPerson -> !checkedPersons.contains(friendOfCurrentPerson))
                        .forEach(friendOfCurrentPerson -> queue.add(currentPath + " -> " + friendOfCurrentPerson));
            }
        }
    }

    /**
     * method parses input contiotions to graph with persons and their friends
     * @param condition input conditions as list of strings. First name is person, other names are friends of this person
     */
    static void fillPersonsList(ArrayList<String> condition) {
        for (String string : condition) {
            String[] names = string.split(", ");

            if (!list.containsKey(names[0]))
                list.put(names[0], new Person(names[0])); // person is not exist in the list

            // add friends
            Person friend;
            for (int i = 1; i < names.length; i++) {
                if (list.containsKey(names[i])) friend = list.get(names[i]); // person's friend is exist in the list
                else {                                                       // person's friend is not exits in the list
                    friend = new Person(names[i]);
                    list.put(friend.name, friend);
                }
                list.get(names[0]).friends.add(friend);
            }
        }
    }
}
