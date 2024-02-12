package todo.list;
import java.util.*;
import java.io.*;

public class ToDoApp {
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        //ArrayList<ToDo> list = new ArrayList<>(); <- new File作成したため不要

        File file = new File("todo.csv");
        ArrayList<ToDo> list;
        if(file.exists()) {
            list = loadFile(file);
        } else {
            list = new ArrayList<>();
        }

        if(list.size() == 0) {
            System.out.println("ToDoは1件もありません。");
        } else {
            displayList(list);
        }

        while(true) {
            System.out.println("--操作を入力してください--");
            System.out.print("1/登録 2/重要度変更 3/削除 4/終了");

            int select = scanner.nextInt();

            switch(select) {
                case 1:
                    addItem(list, scanner);
                    break;

                case 2:
                    updateItem(list, scanner);
                    break;

                case 3:
                    deleteItem(list, scanner);
                    break;

                default:
                    System.out.println("アプリケーションを終了します。");
                    return;
            }
            displayList(list);
        }
    }

    static void sortList(ArrayList<ToDo> list) {
        for(int i = 0; i<list.size()-1; i++) {
            for(int j = i + 1; j < list.size(); j++) {
                if(list.get(i).importance < list.get(j).importance) {
                    ToDo temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }

    static void displayList(ArrayList<ToDo> list) {
        sortList(list);
        for(int i = 0; i < list.size(); i++) {
            System.out.printf("%d・・・%s%n", i, list.get(i).showStatus());
        }
    }

    static void addItem(ArrayList<ToDo> list, Scanner scanner) {
        System.out.println("新規のToDoを作成します。");
        System.out.print("ToDoの内容を入力してください。>>");
        String title = scanner.next();
        System.out.print("重要度を1~10(最大)で入力してください。>>");
        int importance = scanner.nextInt();
        ToDo t = new ToDo(title, importance);
        list.add(t);
    }

    static void updateItem(ArrayList<ToDo> list, Scanner scanner) {
        if(list.size() == 0) {
            System.out.println("まだToDoがありません。");
            return;
        }

        System.out.printf("重要度を変更します。番号を入力してください。 0~%d>>", list.size()-1);
        int num = scanner.nextInt();
        ToDo t = list.get(num);
        System.out.print("重要度を再設定してください");
        int importance = scanner.nextInt();
        t.changeImportance(importance);
    }

    static void deleteItem(ArrayList<ToDo> list, Scanner scanner) {
        System.out.printf("Todoを削除します。番号を入力してください。0~%d>", list.size()-1);
        int num = scanner.nextInt();
        list.remove(num);
        System.out.println("1件削除しました。");
    }

    static ArrayList<ToDo> loadFile(File file) throws Exception {
        ArrayList<ToDo> list = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        String line;

        while((line = br.readLine()) !=null) {
            String[] values = line.split(",");
            String title = values[0];
            int importance = Integer.parseInt(values[1]);
            ToDo t = new ToDo(title, importance);
            list.add(t);
        }

        br.close();
        return list;
    }

    static void saveFile(File file, ArrayList<ToDo> list) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);

        for(ToDo c : list) {
            bw.write(c.toCSV());
            bw.newLine();
        }
        bw.close();
    }
}
