package gamehub.gamebind.cronjob;

import gamehub.gamebind.model.GameBind;
import gamehub.gamebind.repository.GameBindRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RemoveOldBindsJob {

    private static final Logger LOG = LogManager.getLogger(RemoveOldBindsJob.class);

    private GameBindRepository gameBindRepository;

    @Scheduled(cron = "* */30 * * * *")
    public void perform() {
        final Set<GameBind> outdated = gameBindRepository.findGamesToRemove();
        LOG.info(String.format("Found %s outdated games", outdated.size()));
        gameBindRepository.findGamesToRemove().forEach(game -> {
            LOG.debug("Remove outdated game bind: " + game.getGuid());
            gameBindRepository.remove(game);
        });
    }

    @Autowired
    public void setGameBindRepository(GameBindRepository gameBindRepository) {
        this.gameBindRepository = gameBindRepository;
    }
}
