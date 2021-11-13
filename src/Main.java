import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Person> people = new ArrayList<>();
        people.add(new Person("Kasia", 1, Group.G1, true));
        people.add(new Person("Bartek", 1, Group.G1, false));
        people.add(new Person("Tomek", 12, Group.G2, true));
        people.add(new Person("Jacek", 17, Group.G2, true));
        people.add(new Person("Agata", 1, Group.G1, true));
        people.add(new Person("Agata3", 20, Group.G3, false));
        people.add(new Person("Agata4", 9, Group.G3, true));

        System.out.println("List with all people : \n" + people);

        System.out.println("1. Active players sorted by score (desc): \n" + getActivePlayersByScoreDesc(people));
        System.out.println("2. Active players sorted by score (desc) from group G1: \n" + getActivePlayersByScoreDesc(people, Group.G1));
        System.out.println("3. Group with the highest score: \n" + getGroupWithHighestScore(people));
        System.out.println("4. Detail about all teams:  Template:[GroupName SumScore (active players) [inactive players]]\n" + printPoints(people));

    }
     /*
  Zwróć listę aktywnych graczy posortowanych po ich wyniku malejąco
   */

    public static List<Person> getActivePlayersByScoreDesc(List<Person> people) {
        return people.stream()
                .filter(Person::isActive)
                .sorted(Comparator.comparing(Person::getScore, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

  /*
  Zwróć listę aktywnych graczy z danej grupy posortowanych po ich wyniku malejąco
   */

    public static List<Person> getActivePlayersByScoreDesc(List<Person> people, Group group) {
        return people.stream()
                .filter(Person::isActive)
                .filter(person -> person.getTeam().equals(group))
                .sorted(Comparator.comparing(Person::getScore, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    /*
    Zwróć grupę, która posiada najwyższy wynik. Jeżeli wynik wielu grup jest taki sam, zwróć tę, która ma mniejszą liczbę aktywnych członków.
    Jeżeli ta liczba jest również równa, zwróć którąkolwiek z nich.
     */
    public static Group getGroupWithHighestScore(List<Person> people) {
        Map<Group, IntSummaryStatistics> groupMapWithScoreStatistics = people.stream()
                .collect(Collectors.groupingBy(Person::getTeam, Collectors.summarizingInt(Person::getScore)));

        Set<Group> groups = groupMapWithScoreStatistics.keySet();

        long maxScore = 0;
        Group groupWithMaxScore = null;
        int countActivePlayer = 0;

        for (Group group : groups) {
            long groupScore = groupMapWithScoreStatistics.get(group).getSum();
            if (groupScore > maxScore) {
                maxScore = groupScore;
                groupWithMaxScore = group;
                countActivePlayer = getActivePlayers(people, group);
            } else if (groupScore == maxScore) {
                int activePlayersCurrentGroupCount = getActivePlayers(people, group);
                if (activePlayersCurrentGroupCount < countActivePlayer) {
                    groupWithMaxScore = group;
                }
            }
        }
        return groupWithMaxScore;
    }
  /*
  Zwróć listę wyników posortowaną malejąco na podstawie ilości punktów per zespół.
  Pojedynczy String powinien mieć format: "NazwaGrupy CałkowityWynik (lista_aktywnych_członków) [ilość nieaktywnych członków]"
   */

    public static List<String> printPoints(List<Person> people) {
        Map<Group, IntSummaryStatistics> groupMapWithScoreStatistics = people.stream()
                .collect(Collectors.groupingBy(Person::getTeam, Collectors.summarizingInt(Person::getScore)));

        Set<Group> groups2 = groupMapWithScoreStatistics.keySet();
        List<String> teams = new ArrayList<>();

        for (Group group : groups2) {
            long sum = groupMapWithScoreStatistics.get(group).getSum();
            long activePlayers = getActivePlayers(people, group);
            long inactivePlayers = groupMapWithScoreStatistics.get(group).getCount() - activePlayers;
            teams.add(String.format("%s %d (%d) [%d]", group.toString(), sum, activePlayers, inactivePlayers));
        }
        return teams;
    }

    public static int getActivePlayers(List<Person> people, Group group) {
        List<Person> activePlayersList = people.stream()
                .filter(Person::isActive)
                .filter(person -> person.getTeam().equals(group))
                .collect(Collectors.toList());
        return activePlayersList.size();
    }
}
