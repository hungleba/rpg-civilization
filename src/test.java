import characters.CivArcher;
import characters.CivCharacter;

public class test {
	public static void main(String[] args) {
		CivCharacter a = new CivArcher();
		System.out.println(a.getHealth());
		System.out.println(a.getLevel());
		a.addToLevel(7);
		System.out.println(a.getLevel());
		a.addToLevel(2);
		System.out.println(a.getLevel());


	}
}
