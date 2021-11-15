package com.bbexcellence.a3awenni.data

import com.bbexcellence.a3awenni.models.*
import java.text.SimpleDateFormat
import java.util.*

class Datasource {
    fun loadUserData(): List<Offer>{
        return listOf(Offer(1,
            "Cherche développeur",
            AppUser("Both"),
        "Bonjour,\n" +
                "\n" +
                "Je voudrais utiliser Quick JS pour du développement WEB\n" +
                "[Url visible pour les membres Pro]\n" +
                "\n" +
                "Les perfs de Quick JS sont bien meilleurs que node JS\n" +
                "\n" +
                "Je souhaite avoir un devis pour deux choses :\n" +
                "\n" +
                "- faire fonctionner le moteur JS en HTTP\n" +
                "- implémenter l'autoload façon PHP\n" +
                "\n" +
                "Il existe [Url visible pour les membres Pro]\n" +
                "mais je n'arrive pas a le faire marcher\n" +
                "\n" +
                "[Url visible pour les membres Pro]\n" +
                "\n" +
                "Il faudra libérer le code produit et le mettre sur GITHUB\n" +
                "\n" +
                "Cordialement,\n" +
                "Xavier\n" +
                "\n",
        100,
        false,
        SimpleDateFormat("dd/M/yyyy hh:mm").format(Date()),
            SimpleDateFormat("dd/M/yyyy hh:mm").format(Date()),
        100, category = Category.BUSINESS),
            Offer(2,
                "Création d'un site de ecommerce shopify ressemble à une boutique etsy",
                AppUser("Ezzo"),
                "Je désire trouver un prestataire qui pourrait me bâtir un site de E commerce sur Shopy ressemblant dans le design et les fonctionnalités à une boutique Etsy (pour avoir un aperçu se rendre directement sur la plateforme de Etsy sur une boutique pro.)\n" +
                        "Nous avons actuellement 3 boutique sur Etsy et nous avons des demandes pour des commandes plus conséquentes de la part de certains de nos clients. Nous désirons les rediriger vers un site de Ecommerce qui garderait les codes et la navigation de la boutique Etsy car ils connaissent très bien ce mode de fonctionnement/ergonomie et donc la transition serait facile pour eux. Les boutiques Etsy sont très simples mais très fonctionnel.\n" +
                        "Si cette proposition vous interesse, vous pouvez me contacter au [Téléphone visible pour les membres Pro]\n" +
                        "Cordialement",
                50,
                true,
                SimpleDateFormat("dd/M/yyyy hh:mm").format(Date()),
                SimpleDateFormat("dd/M/yyyy hh:mm").format(Date()),
                100, status = OfferStatus.BID_CONFIRMED, emergency = EmergencyLevel.PLANNED),
            Offer(3,
                "Aide création site webflow + webdesign (pour webflow et insta)",
                AppUser("Ben"),
                "Cher(e) codeur(se),\n" +
                        "\n" +
                        "J'ai commencé la création du site Internet sur Webflow, mais je n'ai pas les capacités à aller beaucoup plus loin. Je suis à la recherche d'un expert Webflow et webdesigner de talent pour m'accompagner dans la finalisation du site Internet (crowdfunding immo) :\n" +
                        "\n" +
                        "1. Le code en tant que tel n'a rien de complexe. J'ai juste besoin d'un back-end assez simple (inscription emails)\n" +
                        "2. J'ai besoin que quelqu'un reprenne mon template Webflow pour l'améliorer, notamment au plan visuel avec de la créa graphique\n" +
                        "\n" +
                        "Dans un deuxième temps, il faudra aller un peu plus loin avec la création de nouvelles pages (pages de présentation des investissements immo en crowdfunding).\n" +
                        "\n" +
                        "Le projet est naissant, et par conséquent je recherche quelqu'un qui aura de bonnes idées à me présenter notamment en termes de communication.\n" +
                        "\n" +
                        "A très bientôt,\n" +
                        "\n" +
                        "Guillaume",
                500,
                true,
                SimpleDateFormat("dd/M/yyyy hh:mm").format(Date()),
                SimpleDateFormat("dd/M/yyyy hh:mm").format(Date()),
                100, status = OfferStatus.CLOSED, category = Category.HEALTH_CARE),
            Offer(4,
                "Création d’un programme pour un prototype de jeu de quiz",
                AppUser("Anne"),
                "En début de partie, les pseudos de chaque joueur sont renseignés. Il peut" +
                        " y avoir jusqu’à douze joueurs.\n" +
                        "• Lorsqu’un thème est choisi, une question" +
                        " est tirée aléatoirement parmi les questions de ce" +
                        " thème (dans un fichier Excel, avec une feuille par thème), mais …\n" +
                        "• un joueur ne doit jamais retomber sur une question qui a été déjà" +
                        " posée lors d’une partie à laquelle il a participé, y " +
                        "compris la partie en cours. Si toutes les " +
                        "questions du thème ont déjà été posées en présence de ce joueur," +
                        " un message indique qu’il faut choisir un autre thème en raison de" +
                        " l’épuisement des questions.\n" +
                        "• Au cours d’une partie, un même thème ne peut être choisi plus" +
                        " de 3 fois : si le thème est demandé une quatrième fois (ou plus)," +
                        " un message indique que le thème a déjà été choisi trois fois et qu’il" +
                        " faut donc en choisir un autre.",
                1000,
                false,
                SimpleDateFormat("dd/M/yyyy hh:mm").format(Date()),
                SimpleDateFormat("dd/M/yyyy hh:mm").format(Date()),
                2, category = Category.SOCIAL),
            Offer(5,
                "Refonte d'un script / maintenance d'une plateforme saas",
                AppUser("Jason"),
                "Bonjour,\n" +
                        "\n" +
                        "Nous cherchons un développeur capable d'apporter de la maintenance" +
                        " auprès d'un script déjà existant via un service proposé sur la " +
                        "plateforme Discord.\n" +
                        "\n" +
                        "Notre projet est orienté autour d'un moniteur envoyant " +
                        "automatiquement sur Discord les derniers articles postés " +
                        "sur une plateforme de vêtements en ligne très connue, en " +
                        "fonction de certains critères de recherche.\n" +
                        "\n" +
                        "Dans un premier temps l'utilisateur souscrit un abonnement" +
                        " sur notre site Shopify, il reçoit un ensuite un ID d'abonnement" +
                        " par email qu'il renseigne ensuite sur le serveur Discord. Une " +
                        "fois cette étape réalisée, un espace personnel se crée pour lui et" +
                        " lui permet de pouvoir ajouter des recherches à son moniteur personnel." +
                        " Ce moniteur va ensuite envoyer les derniers articles parus sur la " +
                        "plateforme de vêtements directement dans son espace personnel sur le" +
                        " serveur Discord, lui permettant d'effectuer ainsi un achat rapidement " +
                        "s'il le désire.\n" +
                        "\n" +
                        "Le script est déjà existant et l'infrastructure déjà présente, " +
                        "" +
                        "cependant nous avons plusieurs bugs récurrents au niveau des " +
                        "notifications, de la synchronisation des recherches et de la " +
                        "capacité au bot d'être en ligne constamment sans interruption.\n" +
                        "\n" +
                        "L'objectif est de pouvoir restructurer le projet de façon à ce que" +
                        " les membres puissent constamment recevoir les notifications sans mise" +
                        " hors tension du bot.\n" +
                        "\n" +
                        "Vous devez absolument gérer ces différents langages : TypeScript" +
                        " / JavaScript / PostgreSQL / GitHub\n" +
                        "\n" +
                        "Cette mission pourrait aboutir sur une collaboration régulière en cas de succès.\n" +
                        "\n" +
                        "Cordialement",
                324,
                true,
                SimpleDateFormat("dd/M/yyyy hh:mm").format(Date()),
                SimpleDateFormat("dd/M/yyyy hh:mm").format(Date()),
                25, category = Category.VOLUNTEERING, emergency = EmergencyLevel.PLANNED)
        )
    }
}