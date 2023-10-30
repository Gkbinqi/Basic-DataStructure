public class BSTTest {
    public static void main(String[] args) {
        BSTMap<String, Integer> map = new BSTMap<>();
        for (int i = 0; i < 100; i++) {
            map.put("Hello " + i, i);
        }
        for (int i: map) {
            System.out.println(i);
        }
        System.out.println("another test");
        for (String k: map.keySet()) {
            System.out.println(map.get(k));
        }
    }
}
