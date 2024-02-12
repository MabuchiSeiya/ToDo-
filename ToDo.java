package todo.list;

public class ToDo {
    String title;
    int importance;

    ToDo(String title, int importance) {
        this.title = title;
        this.importance = importance;
    }

    String showStatus() {
        return String.format("%s/重要度:%d", this.title, this.importance);
    }

    void changeImportance(int importance) {
        this.importance = importance;
        System.out.println("重要度を変更しました");
    }

    String toCSV() {
        return String.format("%s,%d", this.title, this.importance);
    }
}
