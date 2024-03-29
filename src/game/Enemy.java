package game;

/**
 * 
 * Az ellenfeleket reprezent�l� absztrakt oszt�ly, a t�bbi faj ennek a lesz�rmazottja.
 *
 */
public abstract class Enemy {
	protected int health; 	//ellenf�l aktu�lis �lete
	protected Road currentRoad; 	//az az �telem, melyen az ellenf�l tart�zkodik
	protected int trappedValue;		//megmutatja, hogy az ellenf�l mennyi id�t t�lt m�g akad�lyon, ha egy�ltal�n akad�lyon van
	
	public EnemyView enemyView;
	
	public abstract Enemy duplicate();
	
	/**
	 * 
	 * Az ellenfelek mozg�s�t megval�s�t� f�ggv�ny.
	 * Vissza�t�ri �rt�ke mutatja, hogy az ellenf�l eljutott-e a V�gzet Hegy�re.
	 *
	 */
	public boolean move() {
		if (!currentRoad.isFinal()) {
			if (trappedValue == 0) {	//amennyiben nincs akad�lyon az ellenf�l, vagy m�r kijutott bel�le
				
				currentRoad.removeEnemy(this);		//ell�p az aktu�lis �tr�l
				currentRoad = currentRoad.getNext(Map.RIGHT);		//lek�rj�k a k�vetkez� utat
				
				if (currentRoad != null) {
					currentRoad.addEnemy(this);		//a k�vektez� �tra �r
					
					if (currentRoad.isTrap()) {		//amennyiben akad�lyra l�pett, akkor a megfelel� v�ltoz� v�ltozik
						trappedValue++;
						
						if (currentRoad.getGemType() != null) {	//ha az akad�lyon er�s�t� var�zsk� van, akkor tov�bb marad az akad�lyon, �gy tov�bb n� az �rt�k
							trappedValue++;
						}
					}
				}
			} else {
				trappedValue--;		//ha akad�lon volt, akkor pr�b�l kiszabadulni bel�le, a megfelel� �rt�k egyel cs�kken
				return false;		//mivel ilyenkor nem mozgott tov�bb, biztosan nem �rte el a V�gzet Hegy�t
			}
		}
		
		return currentRoad.isFinal();
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public void setCurrentRoad(Road road){
		currentRoad = road;
	}
	
	/**
	 * 
	 * Az ellenfelet sebz� f�ggv�ny, ezt az ellenf�rle l�v� tornyok fogj�k h�vni. 
	 * Feladata m�g az is, hogy jelzi, az ellenf�l meghalt-e.
	 *
	 */
	public boolean damage(int damage){
		health -= damage;
		if(health <= 0)
			return true;
		else
			return false;
	}
	
	public int getTrappedValue(){
		return trappedValue;
	}
	
	public int getHealth(){
		return health;
	}
	
	public Road getCurrentRoad(){
		return currentRoad;
	}
	
}
