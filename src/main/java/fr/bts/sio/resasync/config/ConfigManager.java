package fr.bts.sio.resasync.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.bts.sio.resasync.model.entity.Constantes;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import fr.bts.sio.resasync.model.entity.Constantes;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ConfigManager {
    private static Config config;
    private static String configPath; // chemin du fichier de configuration config.json
    private static final ObjectMapper mapper = new ObjectMapper();

    // Charger le fichier JSON et retourner l'objet Config
    public static Config load(String path) throws IOException {
        configPath = path;
        config = mapper.readValue(new File(path), Config.class);
        return config;
    }

    // Récupérer la config
    public static Config get() {
        return config;
    }

    // Sauvegarder la config dans le fichier JSON
    public static void save() throws IOException {
        if (config != null && configPath != null) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(configPath), config);
        } else {
            throw new IllegalStateException("Config ou chemin non initialisé !");
        }
    }

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public void addConstante(Constantes nouvelleConstante) {
        try {
            // Récupérer la config actuelle
            Config config = ConfigManager.get();

            // Ajouter à la liste
            List<Constantes> constantes = config.getConstantes();
            constantes.add(nouvelleConstante);

            // Sauvegarder dans le fichier JSON
            ConfigManager.save();

            System.out.println("Nouvelle constante ajoutée et sauvegardée.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateConstante(String libelle, String nouvelleValeur) throws IOException {
        // chargement du fichier json
        //Config config = load("./config.json");
        Config config = load("src/main/java/fr/bts/sio/resasync/storage/config.json");
        List<Constantes> constantes = config.getConstantes(); // recupération des constantes dans le fichier sous forme de list

        boolean updated = false;
        for (Constantes c : constantes) { // parcourt les constantes de la liste
            if (c.getLibelle().equalsIgnoreCase(libelle)) {
                c.setValeur(nouvelleValeur);
                updated = true;
                break;
            }
        }

        if (updated) {
            // sauvegarde de la nouvelle valeur dans le json
            save();
        } else {
            throw new IllegalArgumentException("Constante non trouvée : " + libelle);
        }
    }

    public static Constantes getConstanteByLibelle(String libelle) {
        List<Constantes> constantes = config.getConstantes();
        Constantes retour = null;
        for (Constantes c : constantes) {
            if(c.getLibelle().equalsIgnoreCase(libelle)) {
                retour = c;
            }
        }
        return retour;
    }
}

