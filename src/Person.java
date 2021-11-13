public class Person {
    private String name;
    private Integer score;
    private Group team;
    private boolean active;

    public Person() {
    }

    public Person(String name, Integer score, Group team, boolean active) {
        this.name = name;
        this.score = score;
        this.team = team;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public Group getTeam() {
        return team;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", team=" + team +
                ", active=" + active +
                '}';
    }
}
