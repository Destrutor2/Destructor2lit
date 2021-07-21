package Archives;

import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

//Ne fonctionne pas encore

public class lastdamager extends BukkitRunnable {
	Player lastdamager;
	Player player;
	public lastdamager(Player player,Player lastdamager){
		this.lastdamager=lastdamager;
		this.player=player;
	}
//	int[] timers=30;

	public void resetlastdamager(Player player){
//		timers[new Utils().getMetadata(player,"joinid").asInt()]=30;
	}

	@Override
	public void run() {

		new Utils().setMetadata(player,"lastdamager",lastdamager.getName());
	}
}
