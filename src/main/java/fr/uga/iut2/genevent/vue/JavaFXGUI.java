package fr.uga.iut2.genevent.vue;

import fr.uga.iut2.genevent.Exception.MauvaisFormatException;
import fr.uga.iut2.genevent.Exception.ObjetNonTrouveException;
import fr.uga.iut2.genevent.Exception.autorisationParticipationException;
import fr.uga.iut2.genevent.controleur.Controleur;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import fr.uga.iut2.genevent.modele.*;
import fr.uga.iut2.genevent.util.CourseUtilitaire;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.validator.routines.EmailValidator;


/**
 * La classe JavaFXGUI est responsable des interactions avec
 * l'utilisa·teur/trice en mode graphique.
 * <p>
 * Attention, pour pouvoir faire le lien avec le
 * {@link fr.uga.iut2.genevent.controleur.Controleur}, JavaFXGUI n'est pas une
 * sous-classe de {@link javafx.application.Application} !
 * <p>
 * Le démarrage de l'application diffère des exemples classiques trouvés dans
 * la documentation de JavaFX : l'interface est démarrée à l'initiative du
 * {@link fr.uga.iut2.genevent.controleur.Controleur} via l'appel de la méthode
 * {@link #demarrerInteraction()}.
 *
 * <h2>
 *     Organisation du code :
 * </h2>
 * <ul>
 *      <li>
 *          declaration des elements
 *      </li>
 *      <li>
 *          methodes de lancement de l'application
 *      </li>
 *      <li>
 *          elements de dialogue globaux
 *      </li>
 *      <li>
 *          page de connexion
 *      </li>
 *      <li>
 *          page evenement
 *      </li>
 *      <li>
 *          page resume
 *      </li>
 *      <li>
 *          page courses
 *      </li>
 *      <li>
 *          page materiel
 *      </li>
 *      <li>
 *          page participants
 *      </li>
 *      <li>
 *          page finances
 *      </li>
 * </ul>
 */
public class JavaFXGUI extends IHM {

    private final Controleur controleur;
    private final CountDownLatch eolBarrier;  // /!\ ne pas supprimer /!\ : suivi de la durée de vie de l'interface


    //-------------------------------------------------------------- éléments vue connexion / creation organisateur
    @FXML private VBox vboxListeOrganisateurs;
    @FXML private TextField newUserForenameTextField;
    @FXML private TextField newUserSurnameTextField;
    @FXML private TextField newUserEmailTextField;
    @FXML private DatePicker newUserDateTextField;
    @FXML private Label labelTitleConnexion;
    @FXML private Button buttonQuitterConnexion;
    @FXML private Button buttonAjouterConnexion;
    @FXML private Button buttonCreerConnexion;

    @FXML private Button buttonRetour;

    //----------------------------------------------------------------- éléments vue selection / creation évènement
    @FXML
    private TextField newEventNameTextField;
    @FXML
    private DatePicker newStartDatePicker;
    @FXML
    private DatePicker newEndDatePicker;
    @FXML
    private Label labelTitre;
    //-------------------------------------------------------------------------------élement vue participants
    @FXML
    private Button boutonajouterCoureur;
    @FXML
    private Button boutonajouterBenevole;
    @FXML
    private Button boutonajouterChronometeur;
    //------------------------------------------------------------------------------- éléments vue resume


    @FXML
    private Label labelTitrePagePrincipale;
    @FXML private Button buttonNavBarResume;
    @FXML private Button buttonNavBarCourse;
    @FXML private Button buttonNavBarParticipant;
    @FXML private Button buttonNavBarMateriel;
    @FXML private Button buttonNavBarFinance;
    @FXML private Button buttonNavBarQuitter;

    @FXML private Button butonAjouterParking;

    @FXML private Label labelBigTitle;
    @FXML private Label labelFilArianne;

    @FXML private Label lMessageBudget;

    @FXML private TextField tfNomEvent;
    @FXML private DatePicker dpDateDebEvent;
    @FXML private DatePicker dpDateFinEvent;

    @FXML private Label labelNombreCoureur;
    @FXML private Label labelNombreBenevole;

    @FXML private Slider sliderBudget;
    @FXML private Label lBudgetResume;




    //tableau matériel

    @FXML private TableView<Materiel> materielTableView;
    @FXML private TableColumn<Materiel,String> columnNom;
    @FXML private TableColumn<Materiel,Integer> columnQuantite;


    //-------------------------------------------------------------------------------------------- elements / vue course
    @FXML private Label labelFilArianneCourse;
    @FXML private VBox vboxListeCours;
    @FXML private Button buttonAjouterCourse;
    @FXML private Button modif1Button;
    @FXML private Button modif2Button;
    @FXML private Button modif3Button;
    @FXML private Button modif4Button;

    @FXML private Button buttonCreerCourse;
    @FXML private DatePicker dpCourse;
    @FXML private TextField tfNomCourse;
    @FXML private TextField tfCooCourse;
    @FXML private TextField tfDistanceCourse;
    @FXML private TextField tfNombreCoureurMax;
    @FXML private TextField tfPrixParCoureur;

    @FXML private Label labelNomCourseDetaille;

    @FXML private TextField tfNomCourseDetaille;
    @FXML private DatePicker dpDateCourseDetaille;
    @FXML private TextField tfLieuCourseDetaille;
    @FXML private TextField tfDistanceCourseDetaille;
    @FXML private TextField tfNombreCoureurMaxDetaille;
    @FXML private TextField tfPrixCourseDetaille;

    @FXML private Button buttonNomCourseDetaille;
    @FXML private Button buttonDateCourseDetaille;
    @FXML private Button buttonLieuCourseDetaille;
    @FXML private Button buttonDistanceCourseDetaille;
    @FXML private Button buttonNombreCoureurCourseDetaille;
    @FXML private Button buttonPrixCourseDetaille;

    @FXML private Label labelConfirmerSuppCourse;
    private Course courseCourante;


    //------------------------------------------------------------------------------------------- elements / vue Parking
    @FXML private Button BoutonAnnuler;
    @FXML private Button BoutonAjouterParking;
    @FXML private Button buttonCreerParking;
    @FXML private TextField nomParking;
    @FXML private TextField adresseParking;
    @FXML private TextField nbPlaceParking;
    @FXML private TableView<Parking> tableView;
    @FXML private TableColumn<Parking, String> colonneNom;
    @FXML private TableColumn<Parking, String>  colonneAdresse;
    @FXML private TableColumn<Parking, Integer> colonnePlaces;


    //------------------------------------------------------------------------------------------- elements / vue Annuler

    @FXML private Button boutonAnnulerpageAnnuler;
    @FXML private Button boutonComfirmer;

    //------------------------------------------------------------------------------------------ elements / vue Materiel

    @FXML private Button buttonAjouterMateriel;
    @FXML private TextField tfNomMateriel;
    @FXML private TextField tfQuantiteMateriel;
    @FXML private TextField tfPrixMateriel;
    @FXML private VBox vbListeMateriel;

    //-------------------------------------------------------------------------------------- elements / vue Participants

    @FXML private VBox vboxListeCoureurs;
    @FXML private Button buttonAjouterCoureur;
    private Coureur coureurCourant;
    @FXML
    private Label titreInscrireCoureur;
    @FXML private  Button inscrireCoureurButton;
    @FXML private TextField nomCoureurTf;
    @FXML private TextField prenomCoureurTf;
    @FXML private DatePicker dateCoureurDp;
    @FXML private TextField mailCoureurTf;
    @FXML private HBox sexeCoureurRb ;
    @FXML private TextField docCoureurTf;
    @FXML private RadioButton femmeRb;
    @FXML private RadioButton hommeRb;
    @FXML private VBox docVB;
    @FXML private Label titreConfirmeCoureur;
    @FXML private Label texteConfirmeCoureur;
    @FXML private VBox vboxListeBenevoles;
    private Benevole benevCourant;
    @FXML private VBox vboxListeChrono;
    private Chronometreur chronoCourant;
    @FXML private Button choisirBouton;
    String typeCourant;
    @FXML private Button inscrireChronoButton;
    @FXML private Button inscrireBenevButton;

    //------------------------------------------------------------------------------------------- elements / vue Finance

    @FXML private Label lSolde;
    @FXML private Slider sBalance;
    @FXML private Label lRevenuTot;
    @FXML private TextField tfMotifRevenu;
    @FXML private TextField tfMontantRevenu;
    @FXML private Label lDepensesTot;
    @FXML private TextField tfMotifDepense;
    @FXML private TextField tfMontantDepense;
    @FXML private DatePicker dpDateRevenu;
    @FXML private DatePicker dpDateDepense;
    @FXML private VBox vbRevenu;
    @FXML private VBox vbDepense;
    @FXML private Label lMessageFinance;
    @FXML private TextField tfBudget;
    @FXML private Button bBudget;
    @FXML private Label lBudgetFinance;



    //--------------------------------------------------------------- méthodes nécessaires au lancement de l'application

    public JavaFXGUI(Controleur controleur) {
        this.controleur = controleur;

        this.eolBarrier = new CountDownLatch(1);  // /!\ ne pas supprimer /!\
    }

    /**
     * Point d'entrée principal pour le code de l'interface JavaFX.
     *
     * @param primaryStage stage principale de l'interface JavaFX, sur laquelle
     *     définir des scenes.
     *
     * @throws IOException si le chargement de la vue FXML échoue.
     *
     * @see javafx.application.Application#start(Stage)
     */
    private void start(Stage primaryStage) throws IOException {
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("connexion.fxml"));
        mainViewLoader.setController(this);
        Scene mainScene = new Scene(mainViewLoader.load());

        Image icon = new Image(getClass().getResource("icon.png").openStream());
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Step by Step");
        primaryStage.setScene(mainScene);
        afficherOrganisateursConnexion();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void demarrerInteraction() {
        // démarrage de l'interface JavaFX
        Platform.startup(() -> {
            Stage primaryStage = new Stage();
            primaryStage.setOnCloseRequest((WindowEvent t) -> this.exitAction());
            try {
                this.start(primaryStage);
            }
            catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        });

        // attente de la fin de vie de l'interface JavaFX
        try {
            this.eolBarrier.await();
        }
        catch (InterruptedException exc) {
            System.err.println("Erreur d'exécution de l'interface.");
            System.err.flush();
        }
    }


    @Override
    public void informerUtilisateur(String msg, boolean succes) {
        final Alert alert = new Alert(
                succes ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING
        );
        alert.setTitle("GenEvent");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    //--------------------------------------------------------------------------------------------- Éléments du dialogue

    //------------------------------------------------------------------------------------------------- éléments globaux
    private void exitAction() {
        // fermeture de l'interface JavaFX : on notifie sa fin de vie
        this.eolBarrier.countDown();
    }


    /**
     * permet de fermer la fenetre courante
     * @param event est l'élement qui appel la methode
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    /**
     * Permet d'afficher la page demandant à l'utilisateur de confirmer son choix lorsqu'il veux quitter l'application
     * @throws IOException
     */

    @FXML
    private void retourAccueilAction(ActionEvent event) {
        closeWindow(event);
        Stage primaryStage = new Stage();
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("connexion.fxml"));
        mainViewLoader.setController(this);
        Scene mainScene = null;
        try {
            mainScene = new Scene(mainViewLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image icon = null;
        try {
            icon = new Image(getClass().getResource("icon.png").openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Step by Step");
        primaryStage.setScene(mainScene);
        afficherOrganisateursConnexion();
        primaryStage.setResizable(false);

        primaryStage.show();
    }
    @FXML
    private void exitMenuItemAction() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.setOnCloseRequest((WindowEvent t) -> this.exitAction());
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("page-quitter.fxml"));
        mainViewLoader.setController(this);
        Scene mainScene = new Scene(mainViewLoader.load());
        Image icon = new Image(getClass().getResource("icon.png").openStream());
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Step by Step");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }


    /**
     * permet de quitter l'application
     */
    @FXML
    private void exitItemAction() {
        Platform.exit();
        this.exitAction();
    }

    //--------------------------------------------------------------------------------------- éléments page de connexion

    /**
     * permet d'afficher la liste d'organisateurs existants à l'execution de l'application
     */
    @FXML
    private void afficherOrganisateursConnexion() {
        this.controleur.afficherOrganisateurs();
    }

    @FXML
    private void creerOrganisateurActionButton(ActionEvent event) {
        this.controleur.saisirNouvelOrganisateur();
    }

    /**
     * action permettant de créer un organisateur
     * @param event
     */
    @FXML
    private void creerOrgaAction(ActionEvent event){
        String prenom = newUserForenameTextField.getText();
        String nom = newUserSurnameTextField.getText();
        String email = newUserEmailTextField.getText();
        LocalDate selectedDate = newUserDateTextField.getValue();
        Date date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        this.controleur.ajouterOrganisateur(nom, prenom, email, date);

        this.controleur.afficherOrganisateurs();
        Node source = (Node) event.getSource();
        Stage stage =(Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * verifie si l'evenement courant existe avant de le retourner
     * @return l'evenement courant s'il existe
     */
    private Evenement getEvenementCourant(){
        if (this.controleur.getGenevent().getOrganisateurs().contains(this.controleur.getOrganisateurCourant()) &&
                this.controleur.getOrganisateurCourant().getEvenements().contains(this.controleur.getEvenementCourant())) {
            return this.controleur.getEvenementCourant();
        } else {
            return null;
        }
    }

    //-------------------------------------------------------------------------------------------- validation des champs

    /**
     * est appelé pour mettre la bordure des textfield en rouge dans le cas ou les informations saisies ne sont pas au
     * format attendu
     * @param textField correspond au textfield dont on souhaite mettre les contours en rouge
     * @param isValid false si la format entré dans le textfield n'est pas au bon format
     */
    private static void markTextFieldErrorStatus(TextField textField, boolean isValid) {
        if (isValid) {
            textField.getStyleClass().remove("tf-with-error");
            textField.getStyleClass().add("tf");
        } else {
            textField.getStyleClass().remove("tf");
            textField.getStyleClass().add("tf-with-error");
        }
    }

    /**
     * est appelé pour mettre la bordure des datepicker en rouge dans le cas ou les informations saisies ne sont pas au
     * format attendu
     * @param datePicker coorespond au datepicker dont les contours vont etre mis en rouge
     * @param isValid true si la valeur dans le date picker est au bon format
     */
    private static void markDatePickerErrorStatus(DatePicker datePicker, boolean isValid) {
        if (isValid) {
            datePicker.getStyleClass().remove("tf-with-error");
        } else {
            datePicker.getStyleClass().add("tf-with-error");
        }
    }



    /**
     * permet de verifier qu'un textfield n'est pas vide
     * @param textField correspond au textfield dont on souhaite verifier le contenu
     * @return true si le format est valide
     */
    private static boolean validateNonEmptyTextField(TextField textField) {
        boolean isValid = textField.getText().strip().length() > 0;

        markTextFieldErrorStatus(textField, isValid);
        return isValid;
    }

    /**
     * permet de verifier qu'un datepicker n'est pas vide
     * @param datePicker le date picker qu'on verifie
     * @return true si le date picker n'est pas vide
     */
    private static boolean validateNonEmptyDatePicker(DatePicker datePicker) {
        boolean isValid=false;
        if (datePicker!=null){
            isValid = datePicker.getValue()!=null;

        }

        markDatePickerErrorStatus(datePicker, isValid);
        return isValid;
    }

    /**
     * verifie si la valeur saisie dans le date picker est une valeur passée
     * @param datePicker le date picker dont on verifie le format
     * @return true si le datepicker est au bon format
     */
    private static boolean validateNaissancePossible(DatePicker datePicker) {
        boolean isValid=true;
        if(datePicker!=null & datePicker.getValue()!=null) {
             isValid = datePicker.getValue().isBefore(LocalDate.now());
        }
        markTextFieldErrorStatus(datePicker, isValid);
        return isValid;
    }

    /**
     * verifie si l'age du coureur contenu dans le date picker est valide pour s'inscrire à une course
     * @param datePicker corresponds
     * @return
     */
    private boolean validateCategorieConforme(DatePicker datePicker) {
        boolean isValid=true;
        if(datePicker!=null & datePicker.getValue()!=null) {
            Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            isValid = CourseUtilitaire.coureurAutorisé(date,this.controleur.getCourseCourante());
        }
        markTextFieldErrorStatus(datePicker, isValid);
        return isValid;
    }



    /**
     * permet de verifier que le contenu d'un textfield ne corresponds pas à un evenement déjà existant
     * @param textField correspond au textfield dont on souhaite verifier le contenu
     * @return true si le format est valide
     */
    private boolean validateNonExistingTextField(TextField textField) {
        boolean isValid = this.controleur.nomExistant(textField.getText());

        markTextFieldErrorStatus(textField, isValid);

        return isValid;
    }

    /**
     * permet de verifier que le contenu d'un textfield correspond au bon format d'email
     * @param textField correspond au textfield dont on souhaite verifier le contenu
     * @return true si le format est valide
     */
    private static boolean validateEmailTextField(TextField textField) {
        EmailValidator validator = EmailValidator.getInstance(false, false);
        boolean isValid = validator.isValid(textField.getText().strip().toLowerCase());

        markTextFieldErrorStatus(textField, isValid);

        return isValid;

    }

    /**
     * permet de verifier que les textfield sont au bon format, dans le cas de creation d'un nouvel utilisateur
     */
    @FXML
    private void validateTextFields() {
        boolean isValid = true;

        isValid &= validateNonEmptyTextField(this.newUserForenameTextField);
        isValid &= validateNonEmptyTextField(this.newUserSurnameTextField);
        isValid &= validateEmailTextField(this.newUserEmailTextField);


        this.buttonCreerConnexion.setDisable(!isValid);
    }

    /**
     * est appelé pour mettre la bordure des datepicker en rouge dans le cas ou les informations saisies ne sont pas au
     * format attendu
     * @param datePicker correspond au datepicker dont on souhaite mettre les contours en rouge
     * @param isValid true si la format entré dans le ditepicker n'est pas au bon format
     */
    private static void markTextFieldErrorStatus(DatePicker datePicker, boolean isValid) {
        if(datePicker!=null) {
            if (isValid) {
                datePicker.getStyleClass().remove("tf-with-error");
            } else {
                datePicker.getStyleClass().add("tf-with-error");
            }
        }
    }

    /**
     * permet de verifier que le contenu d'un datepicker corresponde à une date future
     * @param datePicker correspond au datepicker dont on souhaite verifier le contenu
     * @return true si le format est valide
     */
    private static boolean validateOldDatePicker(DatePicker datePicker){
        boolean isValid=false;
        ChronoLocalDate dt = LocalDate.from(ZonedDateTime.now());
        if(datePicker.getValue()!=null){
            isValid = datePicker.getValue().compareTo(dt)>=0;
            markTextFieldErrorStatus(datePicker,isValid);
        }
        return isValid;
    }

    /**
     * permet de verifier que les dates contenues dans deux datepicker se suivent
     * @return true si le format est valide
     */
    private static boolean validateNewDatePicker(DatePicker newStartDatePicker, DatePicker newEndDatePicker){
        boolean isValid = false;
        if(newEndDatePicker.getValue()!=null & newStartDatePicker.getValue()!=null){
            isValid = newStartDatePicker.getValue().compareTo(newEndDatePicker.getValue())<=0;
            markTextFieldErrorStatus(newEndDatePicker,isValid);
        }
        return isValid;
    }

    /**
     * permet de verifier que les textfield sont au bon format, dans le cas de creation d'un évènement
     */
    @FXML
    private void validateTextFieldsEvent(){
        boolean isValid=true;
        isValid &= validateNonExistingTextField(this.newEventNameTextField);
        isValid &= validateNonEmptyTextField(this.newEventNameTextField);
        isValid &= validateOldDatePicker(this.newStartDatePicker);
        isValid &= validateOldDatePicker(this.newEndDatePicker);
        isValid &= validateNewDatePicker(this.newStartDatePicker,this.newEndDatePicker);

        this.buttonCreerConnexion.setDisable(!isValid);
    }

    /**
     * permet de verifier la date de debut d'evenement lors du clic sur le bouton modif2Button
     */
    @FXML
    private void validateDateDebEvent(){
        boolean isValid=true;

        isValid &= validateOldDatePicker(this.dpDateDebEvent);

        this.modif2Button.setDisable(!isValid);

    }

    /**
     * permet de verifier la date de fin d'evenement lors du clic sur le bouton modif3Button
     */
    @FXML
    private void validateDateFinEvent(){
        boolean isValid=true;

        isValid &= validateOldDatePicker(this.dpDateFinEvent);
        isValid &= validateNewDatePicker(this.dpDateDebEvent,this.dpDateFinEvent);
        this.modif3Button.setDisable(!isValid);

    }

    /**
     * permet de verifier la valeurs contenus dans les texfield lors de la saisie d'une nouvelle course
     */
    @FXML
    private void validateTextFieldsCourse(){
        boolean isValid=true;
        isValid &= validateOldDatePicker(this.dpCourse);
        isValid &= validateDateDansInterval(this.dpCourse);
        isValid &= validateNombreDoubleValideTextField(this.tfDistanceCourse);
        isValid &= validateNombreIntValideTextField(this.tfNombreCoureurMax);
        isValid &= validateNonExistingCourseTextField(this.tfNomCourse);
        isValid &= validateNonEmptyTextField(this.tfNomCourse);
        isValid &= validateGoodCooTextField(this.tfCooCourse);
        isValid &= validateNombreIntValideTextField(this.tfPrixParCoureur);
        this.buttonCreerCourse.setDisable(!isValid);
    }



    /**
     * verifie que la valeur contenue dans un textfield ne correspond pas à une course existante
     * @param textField le textfield dont on verifie la valeur
     * @return true si le champ est valide
     */
    private boolean validateNonExistingCourseTextField(TextField textField) {
        boolean isValid = this.controleur.nomExistantCourse(textField.getText());
        markTextFieldErrorStatus(textField, isValid);
        return isValid;
    }

    /**
     * verifie que la valeur contenue dans un textfield corresponds à un nombre double valide
     * @param textField le texfield dont on verifie la valeur
     * @return true si le champs est valide
     */
    private boolean validateNombreDoubleValideTextField(TextField textField) {
        boolean isValid;
        try {
            // Tentative de conversion en double
            double value = Double.parseDouble(textField.getText());
            isValid = Double.parseDouble(textField.getText()) > 0.0;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        markTextFieldErrorStatus(textField, isValid);
        return isValid;
    }

    /**
     * verifie que la valeur contenue dans un texfield correspond à un nombre int valide
     * @param textField le textfield dont on verifie la valeur
     * @return true si le champs est valide
     */
    private boolean validateNombreIntValideTextField(TextField textField) {
        boolean isValid;
        try {
            // Tentative de conversion en double
            double value = Integer.parseInt(textField.getText());
            isValid = Integer.parseInt(textField.getText()) > 0.0;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        markTextFieldErrorStatus(textField, isValid);
        return isValid;
    }

    /**
     * verifie que la valeur contenue dans un textfield corresponds à des coordonées valides
     * @param textField le texfield dont on verifie le contenu
     * @return true si le champs est valide
     */
    private boolean validateGoodCooTextField(TextField textField) {
        boolean isValid = this.controleur.verifierCoordonees(textField.getText());
        markTextFieldErrorStatus(textField, isValid);
        return isValid;
    }

    /**
     *  verifie que la date saisie dans un datepicker se situe dans l'intervalle de l'evenement courant
     * @param datePicker le datepicker dont on verifie le contenu
     * @return true si le champs est valide
     */
    private boolean validateDateDansInterval(DatePicker datePicker) {
        LocalDate datedeb = this.controleur.getEvenementCourant().getDateDebut();
        LocalDate datefin = this.controleur.getEvenementCourant().getDateFin();
        boolean isValid=false;
        if(datePicker.getValue()!=null){
            isValid= (datePicker.getValue().isAfter(datedeb)|datePicker.getValue().isEqual(datedeb)) & (datePicker.getValue().isBefore(datefin)|datePicker.getValue().isEqual(datefin)) ;
        }
        markTextFieldErrorStatus(datePicker, isValid);
        return isValid;
    }

    /**
     * verifie uniquement le champs contenant la date d'une course
     */
    @FXML
    private void validateTextFieldsDateCourseDetaille(){
        boolean isValid=true;
        isValid &= validateOldDatePicker(this.dpDateCourseDetaille);
        this.buttonDateCourseDetaille.setDisable(!isValid);
    }

    /**
     * verifie uniquement le champs contenant le lieu d'une course
     */
    @FXML
    private void validateTextFieldsLieuCourseDetaille(){
        boolean isValid=true;

        isValid &= validateGoodCooTextField(this.tfLieuCourseDetaille);

        this.buttonLieuCourseDetaille.setDisable(!isValid);
    }

    /**
     * verifie uniquement le champs contenant la distance d'une course
     */
    @FXML
    private void validateTextFieldsDistanceCourseDetaille(){
        boolean isValid=true;

        isValid &= validateNombreDoubleValideTextField(this.tfDistanceCourseDetaille);

        this.buttonDistanceCourseDetaille.setDisable(!isValid);
    }

    /**
     * verifie uniquement le champs contenant le nombre de coureur d'une course
     */
    @FXML
    private void validateTextFieldsNombreCoureurCourseDetaille(){
        boolean isValid=true;

        isValid &= validateNombreIntValideTextField(this.tfNombreCoureurMaxDetaille);

        this.buttonNombreCoureurCourseDetaille.setDisable(!isValid);
    }

    /**
     * verifie uniquement le champs contenant le prix d'une course
     */
    @FXML
    private void validateTextFieldsPrixCourseDetaille(){
        boolean isValid=true;

        isValid &= validateNombreIntValideTextField(this.tfPrixCourseDetaille);
        this.buttonPrixCourseDetaille.setDisable(!isValid);
    }

    /**
     * verifie uniquement le champs contenant le nom d'une course
     */
    @FXML
    private void validateTextFieldsNomCourseDetaille(){
        boolean isValid=true;

        isValid &= validateNonExistingCourseTextField(this.tfNomCourseDetaille);
        isValid &= validateNonEmptyTextField(this.tfNomCourseDetaille);

        this.buttonNomCourseDetaille.setDisable(!isValid);
    }

    /**
     * permet de verifier les champs pour creer un parking
     */
    @FXML
    private void validateParkingEvent(){

        boolean isValid=true;

        isValid &= validateNonExistingParkingTextField(this.nomParking);
        isValid &= validateNonEmptyTextField(this.nomParking);
        isValid &= validateGoodCooTextField(this.adresseParking);
        isValid &= validateNonEmptyTextField(this.adresseParking);
        isValid &= validateNonEmptyTextField(this.nbPlaceParking);
        isValid &= validateNombreIntValideTextField(this.nbPlaceParking);

        this.buttonCreerParking.setDisable(!isValid);


    }

    /**
     * verifie si le contenu d'un texfield ne corresponds pas à un parking existant
     * @param textField le texfield dont on verifie le contenue
     * @return true si le champs est valide
     */
    private boolean validateNonExistingParkingTextField(TextField textField) {
        boolean isValid = !this.controleur.existParking(textField.getText());
        markTextFieldErrorStatus(textField, isValid);
        return isValid;
    }

    /**
     * verification des champs lors de l'ajout d'un materiel
     */
    @FXML
    private void verifMat(){
        boolean isValid = true;

        isValid &= validateNonEmptyTextField(this.tfNomMateriel);
        isValid &= validateNonEmptyTextField(this.tfQuantiteMateriel);
        isValid &= validateNombreIntValideTextField(this.tfQuantiteMateriel);
        isValid &= validateNombreDoubleValideTextField(this.tfPrixMateriel);
        isValid &= validateNonEmptyTextField(this.tfPrixMateriel);
        isValid &= validateNonExistingMaterial(this.tfNomMateriel);
        this.buttonAjouterMateriel.setDisable(!isValid);
    }


    @FXML
    private void validateNameEvent(){
        boolean isValid=true;
//        if(tfNomEvent.getText().compareTo(this.controleur.getEvenementCourant().getNom())==0){
//            markTextFieldErrorStatus(tfNomEvent, isValid);
//            this.modif1Button.setDisable(false);
//        }else {
//            isValid &= validateNonExistingTextField(this.tfNomEvent);
//            isValid &= validateNonEmptyTextField(this.tfNomEvent);
//
//            this.modif1Button.setDisable(!isValid);
//        }
    }


    /**
     * verifie que le contenue d'un textfield ne correspond pas à un materiel existant
     * @param nomMateriel le textfield qu'on souhaite verifier
     * @return true si le champs est valide
     */
    private boolean validateNonExistingMaterial(TextField nomMateriel) {
        boolean isValid = !this.controleur.matExistant(nomMateriel.getText());
        markTextFieldErrorStatus(nomMateriel, isValid);
        return isValid;
    }

    /**
     * permet de verifier les champs de saisie d'un nouveau coureur
     */
    @FXML
    private void validateRunnerFields(){
        boolean isValid=true;
        isValid &= validateNonEmptyTextField(this.nomCoureurTf);

        isValid &= validateNonEmptyTextField(this.prenomCoureurTf);
        isValid &= validateEmailTextField(this.mailCoureurTf);
        isValid &= validateNonEmptyDatePicker(this.dateCoureurDp);
        isValid &= validateNaissancePossible(this.dateCoureurDp);
        isValid &= validateCategorieConforme(this.dateCoureurDp);



        this.inscrireCoureurButton.setDisable(!isValid);
    }

    /**
     * permet de valider les champs de saisie d'un nouveau chronometreur
     */
    @FXML
    private void validateChronoFields(){
        boolean isValid=true;
        isValid &= validateNonEmptyTextField(this.nomCoureurTf);
        isValid &= validateNonEmptyTextField(this.prenomCoureurTf);
        isValid &= validateEmailTextField(this.mailCoureurTf);
        isValid &= validateNonEmptyDatePicker(this.dateCoureurDp);
        isValid &= validateNaissancePossible(this.dateCoureurDp);

        this.inscrireChronoButton.setDisable(!isValid);
    }

    /**
     * permet de valider les champs de saisie d'un nouveau benevole
     */
    @FXML
    private void validateBenevFields(){
        boolean isValid=true;
        isValid &= validateNonEmptyTextField(this.nomCoureurTf);
        isValid &= validateNonEmptyTextField(this.prenomCoureurTf);
        isValid &= validateEmailTextField(this.mailCoureurTf);
        isValid &= validateNonEmptyDatePicker(this.dateCoureurDp);
        isValid &= validateNaissancePossible(this.dateCoureurDp);

        this.inscrireBenevButton.setDisable(!isValid);
    }

    //--------------------------------------------------------------------------------------------------- page connexion

    /**
     * permet d'initialiser la page de connexion en affichant la liste des organisateurs existants
     * @param organisateurs correspond à une arraylist d'organisateur
     */
    @Override
    public void afficherListeOrganisateurVbox(ArrayList<Organisateur> organisateurs) {
        if (organisateurs == null) {
            organisateurs = new ArrayList<>();
        }
        int size = vboxListeOrganisateurs.getChildren().size();
        for (int i = size - 1; i >= 0; i--) {
            Node child = vboxListeOrganisateurs.getChildren().get(i);
            if (child instanceof HBox | child instanceof Button) {
                vboxListeOrganisateurs.getChildren().remove(child);
            }
        }
        if ( organisateurs.size()>0) {
            for (int i=0; i<organisateurs.size(); i++) {
                Button button = new Button(organisateurs.get(i).getNomComplet());
                button.setStyle("-fx-background-color: white;" + "-fx-pref-width: 350px;");
                button.setOnMouseEntered(event ->
                        button.setStyle("-fx-background-color: #a3a3a3;" + "-fx-pref-width: 350px;")
                );

                button.setOnMouseExited(event ->
                        button.setStyle("-fx-background-color: white;" + "-fx-pref-width: 350px;")
                );

                button.setOnAction(ActionEvent -> {
                            //ouvrir la liste d'evenements correspondant à 'utilisateur cliqué. gestion d'evenement dans le cas ou l'utilisateur n'est pas trouvé
                            try {
                                this.controleur.afficherEvenements(button.getText());
                            } catch (ObjetNonTrouveException e) {

                            }
                        }
                );
                Button buttonSupp = new Button("Supprimer");
                buttonSupp.getStyleClass().addAll("button", "button-white");
                ArrayList<Organisateur> finalOrganisateurs = organisateurs;
                int finalI = i;
                HBox hbox = new HBox();
                buttonSupp.setOnAction(ActionEvent -> {
                            buttonSupp.setText(" Valider ");
                            hbox.setMargin(buttonSupp, new Insets(0, 10, 0, 33));
                            buttonSupp.setOnAction(ActionEvent2 -> {
                                        finalOrganisateurs.remove(finalI);
                                        afficherListeOrganisateurVbox(finalOrganisateurs);
                                    }
                            );

                        }
                );
                hbox.getChildren().add(button);
                hbox.getChildren().add(buttonSupp);
                hbox.setAlignment(Pos.CENTER_RIGHT);
                hbox.setMargin(buttonSupp, new Insets(0, 10, 0, 22));
                vboxListeOrganisateurs.getChildren().add(hbox);
            }
        } else {
            Button button = new Button("Aucun organisateur");
            button.setStyle("-fx-background-color: white;" + "-fx-pref-width: 350px;");
            vboxListeOrganisateurs.getChildren().add(button);
        }
    }

    /**
     * ouvre la fenetre de creation d'un organisateur
     */
    @Override
    public void saisirNouvelOrganisateur() {
        try {
            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("create-orga-view.fxml"));
            newUserViewLoader.setController(this);
            Scene newUserScene = new Scene(newUserViewLoader.load());
            Stage newUserWindow = new Stage();
            newUserWindow.setTitle("Creer un organisateur");
            Image icon = new Image(getClass().getResource("icon.png").openStream());
            newUserWindow.getIcons().add(icon);
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            //newUserWindow.showAndWait();
            newUserWindow.setResizable(false);

            newUserWindow.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    //--------------------------------------------------------------------------------------------------- page évènement

    /**
     * permet de créer un nouvel évenement à partir des informations saisies par l'utilisateur
     * @param event
     * @throws ObjetNonTrouveException
     */
    @FXML
    private void creerEventAction(ActionEvent event) throws ObjetNonTrouveException {
        String nom = newEventNameTextField.getText();
        LocalDate dateDebut = newStartDatePicker.getValue();
        LocalDate dateFin = newEndDatePicker.getValue();

        this.controleur.ajouterEvent(nom,dateDebut,dateFin);

        this.controleur.afficherEvenements(this.controleur.getOrganisateurCourant().getNomComplet());
        Node source = (Node) event.getSource();
        Stage stage =(Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * permet d'initaliser la page de choix d'un évènement avec la liste des évènements existants
     * @param events correspond à la liste d'évènement à afficher
     */
    @Override
    public void afficherPageVosEvenements(ArrayList<Evenement> events) {
        labelTitleConnexion.setText("Vos évènements");
        labelTitre.setText("Se connecter à un évènement existant");

        int size = vboxListeOrganisateurs.getChildren().size();
        for (int i = size - 1; i >= 0; i--) {
            Node child = vboxListeOrganisateurs.getChildren().get(i);
            if (child instanceof HBox | child instanceof Button) {
                vboxListeOrganisateurs.getChildren().remove(child);
            }else if (child instanceof Label) {
                Label temp = (Label) child;
                temp.setText("Sélectionner un évènement");
            }
        }

        buttonRetour.setOnAction(ActionEvent -> {
                    retourAccueilAction(ActionEvent);
                }
        );
        buttonRetour.setText("Retour");

        if (events == null) {
            events = new ArrayList<>();
        }
        if (events.size() > 0) {
            for (int i = 0; i < events.size(); i++) {
                Button button = new Button(events.get(i).getNom());
                button.setStyle("-fx-background-color: white;" + "-fx-pref-width: 350px;");
                button.setOnMouseEntered(event ->
                        button.setStyle("-fx-background-color: #a3a3a3;" + "-fx-pref-width: 350px;")
                );

                button.setOnMouseExited(event ->
                        button.setStyle("-fx-background-color: white;" + "-fx-pref-width: 350px;")
                );

                button.setOnAction(ActionEvent -> {
                            //ouvrir la liste d'evenements correspondant à 'utilisateur cliqué.
                            // gestion d'evenement dans le cas ou l'utilisateur n'est pas trouvé
                            try {
                                Scene scene = button.getScene();
                                Stage stage = (Stage) scene.getWindow();
                                stage.close();

                                this.controleur.afficherLEvenement(button.getText());
                            } catch (ObjetNonTrouveException e) {

                            }
                        }
                );

                Button buttonSupp = new Button("Supprimer");
                buttonSupp.getStyleClass().addAll("button", "button-white");
                ArrayList<Evenement> finalEvent = events;
                int finalI = i;
                HBox hbox = new HBox();
                buttonSupp.setOnAction(ActionEvent -> {
                            buttonSupp.setText(" Valider ");
                            hbox.setMargin(buttonSupp, new Insets(0, 10, 0, 33));
                            buttonSupp.setOnAction(ActionEvent2 -> {
                                        finalEvent.remove(finalI);
                                        afficherPageVosEvenements(finalEvent);
                                    }
                            );

                        }
                );
                hbox.getChildren().add(button);
                hbox.getChildren().add(buttonSupp);
                hbox.setAlignment(Pos.CENTER_RIGHT);
                hbox.setMargin(buttonSupp, new Insets(0, 10, 0, 22));
                vboxListeOrganisateurs.getChildren().add(hbox);
            }
        } else {
            Button button = new Button("Aucun évènement");
            button.setStyle("-fx-background-color: white;" + "-fx-pref-width: 350px;");
            vboxListeOrganisateurs.getChildren().add(button);
        }
        buttonAjouterConnexion.setText("Créer un évènement");

        buttonAjouterConnexion.setOnAction(ActionEvent -> {
                    //ouvrir la liste d'evenements correspondant à 'utilisateur cliqué.
                    // Gestion d'evenement dans le cas ou l'utilisateur n'est pas trouvé
                    try {
                        FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("creer-evenement.fxml"));
                        newUserViewLoader.setController(this);
                        Scene newUserScene = new Scene(newUserViewLoader.load());
                        Stage newUserWindow = new Stage();
                        newUserWindow.setTitle("Creer un évènement");
                        Image icon = new Image(getClass().getResource("icon.png").openStream());
                        newUserWindow.getIcons().add(icon);
                        newUserWindow.initModality(Modality.APPLICATION_MODAL);
                        newUserWindow.setScene(newUserScene);
                        //newUserWindow.showAndWait();
                        newUserWindow.setResizable(false);

                        newUserWindow.show();
                    } catch (IOException exc) {
                        throw new RuntimeException(exc);
                    }
                }


        );
    }

//---------------------------------------------------------------------------------------------------------- Page Resume

    /**
     * permet d'afficher les infos principales sur la page resume
     * @param nomEvent le nom de l'evenement courant
     * @param dateDeb la date de debut de l'evenement
     * @param dateFin la date de fin de l'evenement
     * @param budget le budget pour l'evenement
     * @param solde la solde restante pour l'evenement
     * @param nbrCoureur le nombre de coureur total dans l'evenement
     * @param nbrBenev le nombre de benevoles total dans l'evenement
     */
    @Override
    public void afficherInfosPagePrincipale(String nomEvent, Date dateDeb, Date dateFin,
                                            int budget, int solde, int nbrCoureur, int nbrBenev) {



        labelFilArianne.setText("Vos évènements > " + nomEvent + " > Résumé");
        labelBigTitle.setText(nomEvent);

        tfNomEvent.setText(nomEvent);
        tfNomEvent.setDisable(true);
        LocalDate dateDeb2 = dateDeb.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpDateDebEvent.setValue(dateDeb2);
        dpDateDebEvent.setDisable(true);
        LocalDate dateFin2 = dateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpDateFinEvent.setValue(dateFin2);
        dpDateFinEvent.setDisable(true);
        sliderBudget.setDisable(true);

        budgetIsDefine(sliderBudget,lMessageBudget,lBudgetResume);

        labelNombreCoureur.setText(String.valueOf(nbrCoureur));
        labelNombreBenevole.setText(String.valueOf(nbrBenev));
        this.controleur.afficherParking();
        this.controleur.afficherMateriel();
    }




    /**
     * ouvre la page principale lors du clic sur un evenement
     */
    @Override
    public void ouvrirPageEvenement() {
        try {

            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("evenement-x.fxml"));
            newUserViewLoader.setController(this);
            Scene newUserScene = new Scene(newUserViewLoader.load());
            Stage newUserWindow = new Stage();
            newUserWindow.setTitle(this.controleur.getEvenementCourant().getNom());
            Image icon = new Image(getClass().getResource("icon.png").openStream());
            newUserWindow.getIcons().add(icon);
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            //newUserWindow.showAndWait();
            newUserWindow.setResizable(false);
            newUserWindow.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
        if (this.controleur.getDataRevenu().size()==0){
            this.controleur.initialiseEvenement();
        }
        this.controleur.afficherInfoPagePrincipale();

    }

    /**
     * affiche la page resume lors du clique sur le bouton de la barre de navigation
     * @param event corresponds au bouton sur la barre de navigation
     */
    @FXML
    private void affichePageResume(ActionEvent event){
        Button button = (Button) event.getSource();
        Stage fenetre = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("evenement-x.fxml"));
        fxmlLoader.setController(this);
        Parent nouvelleScene = null;
        fenetre.setOnCloseRequest((WindowEvent t) -> this.exitAction());

        try {
            nouvelleScene = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fenetre.setScene(new Scene(nouvelleScene));
        fenetre.setResizable(false);
        fenetre.show();
        this.controleur.afficherInfoPagePrincipale();

    }


    /**
     * ouvre la page parking lors du clique sur le bouton ajouter
     * @throws IOException
     */
    @FXML
    private void butonAjouterParkingEvenement() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.setOnCloseRequest((WindowEvent t) -> this.exitAction());
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("ajout-parking.fxml"));
        mainViewLoader.setController(this);
        Scene mainScene = new Scene(mainViewLoader.load());
        Image icon = new Image(getClass().getResource("icon.png").openStream());
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Ajouter un parking");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }


    /**
     * permet de modifier la valeur contenue dans le texfield nom d'evenement
     */
    @FXML
    private void modif1ButtonAction(){
        if (modif1Button.getText().compareTo("Modifier") == 0) {
            modif1Button.setText("Valider");
            tfNomEvent.setDisable(false);
        }else{
            modif1Button.setText("Modifier");
            tfNomEvent.setDisable(true);
            this.controleur.getEvenementCourant().setNom(tfNomEvent.getText());
            getEvenementCourant().setNom(tfNomEvent.getText());

            this.controleur.afficherInfoPagePrincipale();

        }
    }

    /**
     * permet de modifier la valeur contenue dans le textfield date debut d'evenement
     */
    @FXML
    private void modif2ButtonAction(){
        if (modif2Button.getText().compareTo("Modifier") == 0) {
            modif2Button.setText("Valider");
            dpDateDebEvent.setDisable(false);
        }else{
            modif2Button.setText("Modifier");
            dpDateDebEvent.setDisable(true);
            this.controleur.getEvenementCourant().setDateDebut(dpDateDebEvent.getValue());
            getEvenementCourant().setDateDebut(dpDateDebEvent.getValue());

        }
    }

    /**
     * permet de modifier la valeur contenue dans le texfield date fin d'evenement
     */
    @FXML
    private void modif3ButtonAction(){
        if (modif3Button.getText().compareTo("Modifier") == 0) {
            modif3Button.setText("Valider");
            dpDateFinEvent.setDisable(false);
        }else {
                modif3Button.setText("Modifier");
                dpDateFinEvent.setDisable(true);
                this.controleur.getEvenementCourant().setDateFin(dpDateFinEvent.getValue());
                getEvenementCourant().setDateFin(dpDateFinEvent.getValue());

        }
    }

    // --- elements parking

    /**
     * permet d'ouvrir la page faisant confirmer à l'utilisateur l'annulation de son action
     * @throws IOException
     */
    @FXML
    private void BoutonAnnuler() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.setOnCloseRequest((WindowEvent t) -> this.exitAction());
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("page-annuler.fxml"));
        mainViewLoader.setController(this);
        Scene mainScene = new Scene(mainViewLoader.load());

        primaryStage.setTitle("Step by Step");
        Image icon = new Image(getClass().getResource("icon.png").openStream());
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }


    /**
     * permet d'ajouter un parking une fois le bouton de creation de parking pressé
     * @param event le bouton de creation de parking
     * @throws MauvaisFormatException
     */
    @FXML
    private void ajouterParking(ActionEvent event) throws MauvaisFormatException {

        //récuperer les valeur saisies dans le textField du parking
        String nom = nomParking.getText();
        String adresse = adresseParking.getText();
        int nombrePlaces = Integer.parseInt(nbPlaceParking.getText());

        this.controleur.ajouterParking(nom, adresse, nombrePlaces);
        this.controleur.afficherParking();
        closeWindow(event);
    }

    /**
     * une fois appelée, affiche les parkings existants sur la page de resume
     * @param parkings liste des parkings existants
     */
    @Override
    public void afficherParking(ArrayList<Parking> parkings) {
        //insertion des valeurs dans les colonnes
        colonneNom.setCellValueFactory(feature -> {
            Parking elem = feature.getValue();
            return new SimpleObjectProperty<>(elem.getLibelle());
        });
        colonneAdresse.setCellValueFactory(feature -> {
            Parking elem = feature.getValue();
            return new SimpleObjectProperty<>(elem.getCoordones());
        });
        colonnePlaces.setCellValueFactory(feature -> {
            Parking elem = feature.getValue();
            return new SimpleObjectProperty<>(elem.getNbPlaces());
        });
        tableView.getItems().clear();
        tableView.refresh();
        for (Parking parking : parkings) {
            tableView.getItems().add(parking);
        }
        tableView.refresh();

    }


    /**
     * permet d'afficher la liste de materiel sur la page de resume
     * @param materiels
     */
    @Override
    public void afficherMatériel(ArrayList<Materiel> materiels) {
        //insertion des valeurs dans les colonnes
        columnNom.setCellValueFactory(feature -> {
            Materiel elem = feature.getValue();
            return new SimpleObjectProperty<>(elem.getNom());
        });
        columnQuantite.setCellValueFactory(feature -> {
            Materiel elem = feature.getValue();
            return new SimpleObjectProperty<>(elem.getQuantite());
        });

        materielTableView.getItems().clear();
        materielTableView.refresh();
        for (Materiel materiel : materiels) {
            materielTableView.getItems().add(materiel);
        }
        materielTableView.refresh();
    }


    /**
     * permet de revenir sur la page d'edition apres confirmation de l'utilisateur vias la page annuler
     * @param event le bouton annuler
     * @throws IOException
     */
    @FXML
    private void boutonAnnulerPageAnnuler(ActionEvent event) throws IOException {
        closeWindow(event);
    }

    /**
     * permet d'annuler l'action de l'utilisateur apres confirmation vias la page annuler
     * @throws IOException
     */
    @FXML
    private void boutonConfirmer() throws IOException {
        Stage fenetreAnnuler = (Stage) boutonAnnulerpageAnnuler.getScene().getWindow();
        Stage fenetrePrecedente = (Stage) BoutonAnnuler.getScene().getWindow();
        fenetreAnnuler.close();
        fenetrePrecedente.close();


    }

    //------------------------------------------------------------------------------------------------------ Page course

    /**
     * affiche la page course lors du clique sur le bouton de la barre de navigation
     * @param event corresponds au bouton sur la barre de navigation
     */
    @FXML
    private void affichePageCourses(ActionEvent event){

        Button button = (Button) event.getSource();
        Stage fenetre = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("courses.fxml"));
        fxmlLoader.setController(this);
        Parent nouvelleScene = null;
        fenetre.setOnCloseRequest((WindowEvent t) -> this.exitAction());
        try {
            nouvelleScene = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fenetre.setScene(new Scene(nouvelleScene));
        fenetre.setResizable(false);
        fenetre.show();
        this.controleur.afficherListeCourseExistante();

        labelFilArianne.setText("Vos évènements > " + this.controleur.getEvenementCourant().getNom() + " > Courses");

    }

    /**
     * permet de creer la course lors du clic sur le bouton creer de la fenetre de creation de course
     * @throws MauvaisFormatException
     */
    @FXML
    private void creerCourseActionButton(ActionEvent event) throws MauvaisFormatException {
        LocalDate localDate = dpCourse.getValue();
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        this.controleur.ajouterCourse(tfNomCourse.getText(), date , tfCooCourse.getText(),
                Double.parseDouble(tfDistanceCourse.getText()), Integer.parseInt(tfNombreCoureurMax.getText()),
                Integer.parseInt(tfPrixParCoureur.getText()));
        closeWindow(event);
        this.controleur.afficherListeCourseExistante();
    }

    /**
     * permet d'ouvrir la fenetre permettant d'ajouter une course lors du clic sur un bouton
     */
    @FXML
    private void ajouterCourseActionButton() {
        try {
            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("creer-course.fxml"));
            newUserViewLoader.setController(this);
            Scene newUserScene = new Scene(newUserViewLoader.load());
            Stage newUserWindow = new Stage();
            newUserWindow.setTitle("Créer une course");
            Image icon = new Image(getClass().getResource("icon.png").openStream());
            newUserWindow.getIcons().add(icon);
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.setResizable(false);

            newUserWindow.show();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    private void annulerActionButton(ActionEvent event) {
        closeWindow(event);
    }

    /**
     * permet de modifier le nom d'une course lors du clic sur le bouton modifier
     * @param event le bouton sur lequel on clic pour modifier le nom
     */
    @FXML
    private void modifierNomCourse(ActionEvent event) {

        Button button = (Button) event.getSource();
        Course course = courseCourante;
        tfNomCourseDetaille.setDisable(false);
        button.setText("Valider");
        button.setOnAction(ActionEvent -> {
                    button.setText("Modifier");

                    course.setLabel(tfNomCourseDetaille.getText());
                    tfNomCourseDetaille.setDisable(true);
                    button.setDisable(false);
                    button.setOnAction(ActionEvent2 -> {
                                modifierNomCourse(event);
                            }
                    );
                }
        );
    }

    /**
     * permet de modifier la date d'une course lors du clic sur le bouton modifier
     * @param event le bouton sur lequel on clic pour modifier la date
     */
    @FXML
    private void modifierDateCourse(ActionEvent event) {

        Button button = (Button) event.getSource();
        Course course = courseCourante;
        dpDateCourseDetaille.setDisable(false);
        button.setText("Valider");
        button.setOnAction(ActionEvent -> {
                    button.setText("Modifier");

                    ZoneId zoneId = ZoneId.systemDefault(); // Obtenez le fuseau horaire par défaut
                    Date date = Date.from(dpDateCourseDetaille.getValue().atStartOfDay(zoneId).toInstant());
                    course.setDate(date);
                    dpDateCourseDetaille.setDisable(true);
                    button.setDisable(false);
                    button.setOnAction(ActionEvent2 -> {
                                modifierDateCourse(event);
                            }
                    );
                }
        );
    }

    /**
     * permet de modifier le lieu d'une course lors du clic sur le bouton modifier
     * @param event le bouton sur lequel on clic pour modifier le lieu
     */
    @FXML
    private void modifierLieuCourse(ActionEvent event) {

        Button button = (Button) event.getSource();
        Course course = courseCourante;
        tfLieuCourseDetaille.setDisable(false);
        button.setText("Valider");
        button.setOnAction(ActionEvent -> {
                    try {
                        course.setLieux(tfLieuCourseDetaille.getText());
                    } catch (MauvaisFormatException e) {
                        throw new RuntimeException(e);
                    }
                    button.setText("Modifier");

                    tfLieuCourseDetaille.setDisable(true);
                    button.setDisable(false);
                    button.setOnAction(ActionEvent2 -> {
                                modifierLieuCourse(event);
                            }
                    );
                }
        );
    }

    /**
     * permet de modifier la distance d'une course lors du clic sur le bouton modifier
     * @param event le bouton sur lequel on clic pour modifier la distance
     */
    @FXML
    private void modifierDistanceCourse(ActionEvent event) {

        Button button = (Button) event.getSource();
        Course course = courseCourante;
        tfDistanceCourseDetaille.setDisable(false);
        button.setText("Valider");
        button.setOnAction(ActionEvent -> {
                    button.setText("Modifier");

                    course.setLongueur(Double.parseDouble(tfDistanceCourseDetaille.getText()) );
                    tfDistanceCourseDetaille.setDisable(true);
                    button.setDisable(false);
                    button.setOnAction(ActionEvent2 -> {
                                modifierDistanceCourse(event);
                            }
                    );
                }
        );
    }

    /**
     * permet de modifier le nombre de coureur d'une course lors du clic sur le bouton modifier
     * @param event le bouton sur lequel on clic pour modifier le nombre de coureur
     */
    @FXML
    private void modifierNombreCoureurCourse(ActionEvent event) {

        Button button = (Button) event.getSource();
        Course course = courseCourante;
        tfNombreCoureurMaxDetaille.setDisable(false);
        button.setText("Valider");
        button.setOnAction(ActionEvent -> {
                    button.setText("Modifier");

                    course.setMaxCoureur(Integer.parseInt(tfNombreCoureurMaxDetaille.getText()));
                    tfNombreCoureurMaxDetaille.setDisable(true);
                    button.setDisable(false);
                    button.setOnAction(ActionEvent2 -> {
                                modifierNombreCoureurCourse(event);
                            }
                    );
                }
        );
    }

    /**
     * permet de modifier le prix d'une course lors du clic sur le bouton modifier
     * @param event le bouton sur lequel on clic pour modifier le prix
     */
    @FXML
    private void modifierPrixCourse(ActionEvent event) {

        Button button = (Button) event.getSource();
        Course course = courseCourante;
        tfPrixCourseDetaille.setDisable(false);
        button.setText("Valider");
        button.setOnAction(ActionEvent -> {
                    button.setText("Modifier");
                    course.setPrix(Integer.parseInt(tfPrixCourseDetaille.getText()));
                    tfPrixCourseDetaille.setDisable(true);
                    button.setDisable(false);
                    button.setOnAction(ActionEvent2 -> {
                                modifierPrixCourse(event);
                            }
                    );
                }
        );
    }

    /**
     * permet de supprimer une course (apres validation de l'utilisateur) lors du clic sur le bouton supprimer
     * @param event le bouton supprimer
     */
    @FXML
    private void supprimerCourseActionButton(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage fenetre = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("valider-suppression-course.fxml"));
        fxmlLoader.setController(this);
        Parent nouvelleScene = null;
        try {
            nouvelleScene = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fenetre.setTitle("Suppression de " + courseCourante.getLabel());
        Image icon = null;
        try {
            icon = new Image(getClass().getResource("icon.png").openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fenetre.getIcons().add(icon);
        fenetre.setScene(new Scene(nouvelleScene));
        fenetre.setResizable(false);

        fenetre.show();

        labelConfirmerSuppCourse.setText("Voulez-vous vraiment supprimer " + courseCourante.getLabel() + " ?");


    }

    /**
     * permet de fermer la fenetre d'edition de course et d'actualiser la liste
     * @param event
     */
    @FXML
    private void fermerCourseActionButton(ActionEvent event) {
        closeWindow(event);
        this.controleur.afficherListeCourseExistante();
    }

    /**
     * permet de supprimer une course
     * @param event le bouton executant l'action
     */
    @FXML
    private void ValidersuppCourseActionButton(ActionEvent event) {
        Course course = courseCourante;
        this.controleur.retirerCourse(course);
        closeWindow(event);
        this.controleur.afficherListeCourseExistante();
    }

    /**
     * permet d'afficher la liste de course sur la page course
     * @param courses liste des courses existantes
     * @param nomEvent le nom de l'évènement courant
     */
    @Override
    public void afficherInfosPageCourses(ArrayList<Course> courses, String nomEvent) {
        vboxListeCours.getChildren().clear();

        if (courses==null || courses.isEmpty()) {
            HBox hbox = new HBox();
            Label textCourse = new Label("Aucune course");
            textCourse.getStyleClass().add("button-liste-course");
            hbox.getChildren().add(textCourse);
            vboxListeCours.getChildren().add(hbox);
        }

        for (Course course : courses) {

            Button button = new Button(course.getLabel());

            button.getStyleClass().add("button-liste-course");
            button.setOnAction(ActionEvent -> {
                        courseCourante = course;
                        this.controleur.afficherCourseDetaille(course);
                    }
            );
            vboxListeCours.getChildren().add(button);
        }
    }

    /**
     * permet d'afficher la liste des course pour la page course
     * @param courses la liste des courses existantes
     * @param nomEvent le nom de l'evenement courant
     */
    @Override
    public void selectInfosPageCourses(ArrayList<Course> courses, String nomEvent) {
        vboxListeCours.getChildren().clear();

        if (courses==null || courses.isEmpty()) {
            HBox hbox = new HBox();
            Label textCourse = new Label("Aucune course");
            textCourse.getStyleClass().add("button-liste-course2");
            hbox.getChildren().add(textCourse);
            vboxListeCours.getChildren().add(hbox);
        }

        for (Course course : courses) {

            Button button = new Button(course.getLabel());

            button.getStyleClass().add("button-liste-course2");
            button.setOnAction(ActionEvent -> {
                        courseCourante = course;
                        choisirBouton.setDisable(false);
                        for(int i=0;i<vboxListeCours.getChildren().size();i++){
                            vboxListeCours.getChildren().get(i).getStyleClass().remove("button-selected-course");
                        }
                        button.getStyleClass().add("button-selected-course");

                    }
            );
            vboxListeCours.getChildren().add(button);
        }
    }

    /**
     * permet d'afficher la fenetre contenant les details d'une course
     * @param uneCourse la course dont on veux afficher les details
     */
    @Override
    public void afficherCourseDetaille(Course uneCourse) {
        Stage primaryStage = new Stage();
        primaryStage.setOnCloseRequest((WindowEvent t) -> this.exitAction());
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("affichage-detaille-course.fxml"));
        mainViewLoader.setController(this);
        Scene mainScene = null;
        try {
            mainScene = new Scene(mainViewLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        primaryStage.setTitle(uneCourse.getLabel());
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);

        primaryStage.show();

        labelNomCourseDetaille.setText(uneCourse.getLabel());
        tfNomCourseDetaille.setText(uneCourse.getLabel());
        tfNomCourseDetaille.setDisable(true);
        LocalDate localDate = uneCourse.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpDateCourseDetaille.setValue(localDate);
        dpDateCourseDetaille.setDisable(true);
        tfLieuCourseDetaille.setText(uneCourse.getLieux());
        tfLieuCourseDetaille.setDisable(true);
        tfDistanceCourseDetaille.setText(Double.toString(uneCourse.getLongueur()));
        tfDistanceCourseDetaille.setDisable(true);
        tfNombreCoureurMaxDetaille.setText(Integer.toString(uneCourse.getMaxCoureur()));
        tfNombreCoureurMaxDetaille.setDisable(true);
        tfPrixCourseDetaille.setText(Integer.toString(uneCourse.getPrix()));
        tfPrixCourseDetaille.setDisable(true);
    }

    //---------------------------------------------------------------------------------------------------- Page materiel

    /**
     * affiche la page materiel lors du clique sur le bouton de la barre de navigation
     * @param event corresponds au bouton sur la barre de navigation
     */
    @FXML
    private void affichePageMateriel(ActionEvent event){

        Button button = (Button) event.getSource();
        Stage fenetre = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("materiel.fxml"));
        fxmlLoader.setController(this);
        Parent nouvelleScene = null;
        fenetre.setOnCloseRequest((WindowEvent t) -> this.exitAction());

        try {
            nouvelleScene = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fenetre.setScene(new Scene(nouvelleScene));
        fenetre.setResizable(false);
        fenetre.show();
        this.controleur.afficherPageMat();
        labelFilArianne.setText("Vos évènements > " + this.controleur.getEvenementCourant().getNom() + " > Matériel");
    }


    /**
     * permet d'ajouter un materiel une fois les information correctement saisies par l'utilisateur
     * @param event le bouton permettant d'ajouter le materiel
     */
    @FXML
    private void ajouterMaterielActionButton(ActionEvent event) {
        this.controleur.ajouterMateriel(tfNomMateriel.getText(), Integer.parseInt(tfQuantiteMateriel.getText()), Double.parseDouble(tfPrixMateriel.getText()) );
        tfNomMateriel.setText("");
        tfQuantiteMateriel.setText("");
        tfPrixMateriel.setText("");
        this.controleur.afficherPageMat();
        this.controleur.getDataDepense().get(0).setMontant(this.controleur.getCoutMateriel());
    }

    /**
     * permet d'afficher le contenu de la page materiel
     * @param materiels liste de materiel existant
     */
    @Override
    public void afficherInfosPageMateriel(ArrayList<Materiel> materiels) {
        vbListeMateriel.getChildren().clear();
        if (materiels == null || materiels.isEmpty()) {
            Label textMat = new Label("Aucun matériel renseigné");
            textMat.setStyle("-fx-font-size: 25px");
            vbListeMateriel.getChildren().add(textMat);
        } else {
            for (Materiel mat : materiels) {
                Label labelMat = new Label(" - " + mat.getQuantite() + " " + mat.getNom() + "(s), pour un prix total de " + Math.round(mat.getPrixLocation()*mat.getQuantite() * 100.0) / 100.0  + " € (" + mat.getPrixLocation() + "€/u)    ");
                labelMat.setStyle("-fx-font-size: 20px;");

                Button buttonSupp = new Button("Supprimer");
                buttonSupp.getStyleClass().add("button");
                buttonSupp.getStyleClass().add("button-white");
                buttonSupp.setOnAction(ActionEvent -> {
                            buttonSupp.setText("Valider");
                            buttonSupp.setOnAction(ActionEvent2 -> {
                                        this.controleur.removeMateriel(mat);
                                        afficherInfosPageMateriel(materiels);
                                    }
                            );
                        }
                );
                HBox hbox = new HBox();
                hbox.getChildren().add(labelMat);
                hbox.getChildren().add(buttonSupp);
                vbListeMateriel.getChildren().add(hbox);
            }
        }
    }



    //------------------------------------------------------------------------------------------------ page participants

    /**
     * affiche la page participants lors du clique sur le bouton de la barre de navigation
     * @param event corresponds au bouton sur la barre de navigation
     */
    @FXML
    private void affichePageParticipants(ActionEvent event){

        Button button = (Button) event.getSource();
        Stage fenetre = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("participants.fxml"));
        fxmlLoader.setController(this);
        Parent nouvelleScene = null;
        fenetre.setOnCloseRequest((WindowEvent t) -> this.exitAction());

        try {
            nouvelleScene = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fenetre.setScene(new Scene(nouvelleScene));
        this.controleur.afficherListeCoureursExistante();
        this.controleur.afficherListeBenevsExistante();
        this.controleur.afficherListeChronosExistante();
        fenetre.setResizable(false);
        fenetre.show();
        labelFilArianne.setText("Vos évènements > " + this.controleur.getEvenementCourant().getNom() + " > Participants");

    }
    /**
     * permet d'afficher les coureurs existants sur la page participants
     * @param coureurs liste des coureurs existants
     * @param nomEvent le nom de l'evenement courant
     */
    public void afficherInfosPageCoureurs(ArrayList<Coureur> coureurs, String nomEvent) {
        vboxListeCoureurs.getChildren().clear();

        if (coureurs==null || coureurs.isEmpty()) {
            HBox hbox = new HBox();
            Label textCourse = new Label("Aucun coureur");
            textCourse.getStyleClass().add("button-liste-coureur");
            hbox.getChildren().add(textCourse);
            vboxListeCoureurs.getChildren().add(hbox);
        }

        for (Coureur coureur : coureurs) {

            Button button = new Button(coureur.getNom() + " - " + coureur.getCourse().getLabel());

            button.getStyleClass().add("button-liste-coureur");
            button.setOnAction(ActionEvent -> {
                coureurCourant = coureur;
                        this.controleur.afficherCoureurDetaille(coureur);
                    }
            );
            vboxListeCoureurs.getChildren().add(button);
        }
    }

    /**
     * permet d'afficher les benevoles existants sur la page participants
     * @param benevoles liste des benevoles existants
     * @param nomEvent le nom de l'evenement courant
     */
    public void afficherInfosPageBenevs(ArrayList<Benevole> benevoles, String nomEvent) {
        vboxListeBenevoles.getChildren().clear();
        if (benevoles==null || benevoles.isEmpty()) {
            HBox hbox = new HBox();
            Label textCourse = new Label("Aucun bénévole");
            textCourse.getStyleClass().add("button-liste-coureur");
            hbox.getChildren().add(textCourse);
            vboxListeBenevoles.getChildren().add(hbox);
        }

        for (Benevole benevole : benevoles) {

            Button button = new Button(benevole.getNom() + " - " + benevole.getCourse().getLabel());

            button.getStyleClass().add("button-liste-coureur");
            button.setOnAction(ActionEvent -> {
                        benevCourant = benevole;
                        // modification du benev si clic
                        //this.controleur.afficherCoureurDetaille(benevole);
                    }
            );
            vboxListeBenevoles.getChildren().add(button);
        }
    }

    /**
     * permet d'afficher les chronometreurs existants sur la page participants
     * @param chronometreurs liste des chronometreurs existants
     * @param nomEvent le nom de l'evenement courant
     */
    @Override
    public void afficherInfosPageChronos(ArrayList<Chronometreur> chronometreurs, String nomEvent) {
        vboxListeChrono.getChildren().clear();

        if (chronometreurs==null || chronometreurs.isEmpty()) {
            HBox hbox = new HBox();
            Label textCourse = new Label("Aucun chronométreur");
            textCourse.getStyleClass().add("button-liste-coureur");
            hbox.getChildren().add(textCourse);
            vboxListeChrono.getChildren().add(hbox);
        }

        for (Chronometreur chronometreur : chronometreurs) {

            Button button = new Button(chronometreur.getNom() + " - " + chronometreur.getCourse().getLabel());

            button.getStyleClass().add("button-liste-coureur");
            button.setOnAction(ActionEvent -> {
                        chronoCourant = chronometreur;
                        // modification du chronométreur si clic
                        //this.controleur.afficherCoureurDetaille(chronometreur);
                    }
            );
            vboxListeChrono.getChildren().add(button);
        }
    }

    @Override
    public void afficherCoureurDetaille(Coureur unCoureur) {
//        Stage primaryStage = new Stage();
//        primaryStage.setOnCloseRequest((WindowEvent t) -> this.exitAction());
//        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("affichage-detaille-course.fxml"));
//        mainViewLoader.setController(this);
//        Scene mainScene = null;
//        try {
//            mainScene = new Scene(mainViewLoader.load());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        primaryStage.setTitle(unCoureur.getNom());
//        Image icon = null;
//        try {
//            icon = new Image(getClass().getResource("icon.png").openStream());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        primaryStage.getIcons().add(icon);
//        primaryStage.setScene(mainScene);
//        primaryStage.show();


    }

    /**
     * permet d'ajouter un coureur lors du clic sur le bouton ajouter. ouvre une nouvelle fenetre pour saisir les informations
     */
    @FXML
    private void ajouterCoureurActionButton() {
        try {
            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("selection-course.fxml"));
            newUserViewLoader.setController(this);
            Scene newUserScene = new Scene(newUserViewLoader.load());
            Stage newUserWindow = new Stage();
            newUserWindow.setTitle("Choisir une course");
            Image icon = new Image(getClass().getResource("icon.png").openStream());
            newUserWindow.getIcons().add(icon);
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.setResizable(false);

            newUserWindow.show();
            this.controleur.selectListeCourseExistante();
            typeCourant="Coureur";

        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * permet d'ajouter un chronometreur lors du clic sur le bouton ajouter. ouvre une nouvelle fenetre pour saisir les informations
     */
    @FXML
    private void ajouterChronoActionButton() {
        try {
            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("selection-course.fxml"));
            newUserViewLoader.setController(this);
            Scene newUserScene = new Scene(newUserViewLoader.load());
            Stage newUserWindow = new Stage();
            newUserWindow.setTitle("Choisir une course");
            Image icon = new Image(getClass().getResource("icon.png").openStream());
            newUserWindow.getIcons().add(icon);
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.setResizable(false);

            newUserWindow.show();
            this.controleur.selectListeCourseExistante();
            typeCourant="Chrono";

        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * permet d'ajouter un benevole lors du clic sur le bouton ajouter. ouvre une nouvelle fenetre pour saisir les informations
     */
    @FXML
    private void ajouterBenevActionButton() {
        try {
            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("selection-course.fxml"));
            newUserViewLoader.setController(this);
            Scene newUserScene = new Scene(newUserViewLoader.load());
            Stage newUserWindow = new Stage();
            newUserWindow.setTitle("Choisir une course");
            Image icon = new Image(getClass().getResource("icon.png").openStream());
            newUserWindow.getIcons().add(icon);
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.setResizable(false);

            newUserWindow.show();
            this.controleur.selectListeCourseExistante();
            typeCourant="Benev";

        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }


    /**
     * fait choisir à l'utilisateur une courser à selectionner
     */
    @FXML
    private void choisirCourseBoutonAction(ActionEvent event) throws MauvaisFormatException {
        for (int i=0;i<vboxListeCours.getChildren().size();i++){
                if(vboxListeCours.getChildren().get(i).getStyleClass().size()==3){
                    Course courseSelected = this.controleur.getEvenementCourant().getListeCourse().get(i);
                    this.controleur.setCourseCourante(courseSelected);
                    System.out.println(this.controleur.getCourseCourante().getLabel());
                }
        }

        closeWindow(event);
        if(typeCourant.compareTo("Coureur")==0){
            try {
                FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("inscription-course-coureur.fxml"));
                newUserViewLoader.setController(this);
                Scene newUserScene = new Scene(newUserViewLoader.load());
                Stage newUserWindow = new Stage();
                newUserWindow.setTitle("Inscrire un coureur");
                newUserWindow.initModality(Modality.APPLICATION_MODAL);
                newUserWindow.setScene(newUserScene);
                newUserWindow.setResizable(false);

                newUserWindow.show();
                this.controleur.selectListeCourseExistante();
                titreInscrireCoureur.setText("Inscription coureur à " + this.controleur.getCourseCourante().getLabel());

            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }
        else if(typeCourant.compareTo("Chrono")==0){
            try {
                FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("inscription-course-chrono.fxml"));
                newUserViewLoader.setController(this);
                Scene newUserScene = new Scene(newUserViewLoader.load());
                Stage newUserWindow = new Stage();
                newUserWindow.setTitle("Inscrire un chronométreur");
                newUserWindow.initModality(Modality.APPLICATION_MODAL);
                newUserWindow.setScene(newUserScene);
                newUserWindow.setResizable(false);

                newUserWindow.show();
                this.controleur.selectListeCourseExistante();
                titreInscrireCoureur.setText("Inscription chronométreur à " + this.controleur.getCourseCourante().getLabel());

            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }
        else{
            try {
                FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("inscription-course-benev.fxml"));
                newUserViewLoader.setController(this);
                Scene newUserScene = new Scene(newUserViewLoader.load());
                Stage newUserWindow = new Stage();
                newUserWindow.setTitle("Inscrire un bénévole");
                newUserWindow.initModality(Modality.APPLICATION_MODAL);
                newUserWindow.setScene(newUserScene);
                newUserWindow.setResizable(false);

                newUserWindow.show();
                this.controleur.selectListeCourseExistante();
                titreInscrireCoureur.setText("Inscription bénévole à " + this.controleur.getCourseCourante().getLabel());

            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }



    }


    /**
     * permet d'ajouter un nouveau coureur à la liste de coureur.
     * @param event le bouton pour ajouter un coureur
     * @throws ObjetNonTrouveException
     * @throws autorisationParticipationException
     */
    @FXML
    private void inscrireCoureurAction(ActionEvent event) throws ObjetNonTrouveException, autorisationParticipationException {
        String nom = nomCoureurTf.getText();
        String prenom = prenomCoureurTf.getText();
        String email = mailCoureurTf.getText();
        String sexe;
        Course courseCourante = this.controleur.getCourseCourante();
        if(hommeRb.isSelected()){
          sexe=hommeRb.getText();
        }
        else{
            sexe=femmeRb.getText();
        }
        Date date = Date.from(dateCoureurDp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        ArrayList<String> listeDoc = new ArrayList<>();
        TextField docI;
        for(int i=0;i<docVB.getChildren().size();i++){
            docI=(TextField) docVB.getChildren().get(i);
            listeDoc.add(docI.getText());
        }

        Coureur coureurNew = new Coureur(nom,prenom,email,sexe,date,listeDoc,this.controleur.getCourseCourante());
        this.controleur.setCoureurCourant(coureurNew);
        this.controleur.getCourseCourante().addCoureur(coureurNew);
        this.controleur.getEvenementCourant().getLstCoureur().add(coureurNew);


        Node source = (Node) event.getSource();
        Stage stage =(Stage) source.getScene().getWindow();
        stage.close();


        this.controleur.afficherListeCoureursExistante();

        try {
            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("confirmation-coureur.fxml"));
            newUserViewLoader.setController(this);
            Scene newUserScene = new Scene(newUserViewLoader.load());
            Stage newUserWindow = new Stage();
            newUserWindow.setTitle("Confirmer l'inscription");
            newUserWindow.initModality(Modality.APPLICATION_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.setResizable(false);

            newUserWindow.show();
            texteConfirmeCoureur.setText(prenom + " " + nom + " sera inscrit à la course " + courseCourante.getLabel() + " pour un montant de " + courseCourante.getPrix() + " euros.");

        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }


    }

    /**
     * permet d'ajouter un nouveau chronometreur à la liste de chronometreurs.
     * @param event le bouton pour ajouter un chronometreur
     * @throws ObjetNonTrouveException
     * @throws autorisationParticipationException
     */
    @FXML
    private void inscrireChronoAction(ActionEvent event) throws ObjetNonTrouveException, autorisationParticipationException {
        String nom = nomCoureurTf.getText();
        String prenom = prenomCoureurTf.getText();
        String email = mailCoureurTf.getText();
        String sexe;
        if(hommeRb.isSelected()){
            sexe=hommeRb.getText();
        }
        else{
            sexe=femmeRb.getText();
        }
        Date date = Date.from(dateCoureurDp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());


        Chronometreur chronoNew = new Chronometreur(nom,prenom,email,sexe,date,this.controleur.getCourseCourante());
        this.controleur.setChronoCourant(chronoNew);
        this.controleur.getCourseCourante().getListeChrono().add(chronoNew);
        this.controleur.getEvenementCourant().getLstChronometreur().add(chronoNew);

        Node source = (Node) event.getSource();
        Stage stage =(Stage) source.getScene().getWindow();
        stage.close();


        this.controleur.afficherListeChronosExistante();

    }


    /**
     * permet d'ajouter un nouveau benevole à la liste de benevoles.
     * @param event le bouton pour ajouter un benevole
     * @throws ObjetNonTrouveException
     * @throws autorisationParticipationException
     */
    @FXML
    private void inscrireBenevAction(ActionEvent event) throws ObjetNonTrouveException, autorisationParticipationException {
        String nom = nomCoureurTf.getText();
        String prenom = prenomCoureurTf.getText();
        String email = mailCoureurTf.getText();
        String sexe;
        if(hommeRb.isSelected()){
            sexe=hommeRb.getText();
        }
        else{
            sexe=femmeRb.getText();
        }
        Date date = Date.from(dateCoureurDp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());


        Benevole benevNew = new Benevole(nom,prenom,email,sexe,date,this.controleur.getCourseCourante());
        this.controleur.setBenevCourant(benevNew);
        this.controleur.getCourseCourante().getListeBenevole().add(benevNew);
        this.controleur.getEvenementCourant().getLstBenevole().add(benevNew);


        Node source = (Node) event.getSource();
        Stage stage =(Stage) source.getScene().getWindow();
        stage.close();


        this.controleur.afficherListeBenevsExistante();

    }

    /**
     * permet d'ajouter un document à la liste de documents
     */
    @FXML
    private void addDocAction(){
        TextField docCoureurTf1 = new TextField();
        docCoureurTf1.setPromptText("https://mondoc.pdf");
        docVB.getChildren().add(docCoureurTf1);

    }

    @FXML private void confirmeCoureurAction(ActionEvent event){
        // ajouter a finance le prix
        this.controleur.addInscription(courseCourante.getPrix());
        closeWindow(event);
    }

    /**
     * permet d'annuler l'inscription d'un coureur
     * @param event le bouton annuler
     */
    @FXML
    private void annulerInscrireButton(ActionEvent event) {
        this.controleur.getCourseCourante().getListeCoureur().remove(coureurCourant);
        this.controleur.getEvenementCourant().getLstCoureur().remove(coureurCourant);
        closeWindow(event);
    }





    //----------------------------------------------------------------------------------------------------- page finance

    /**
     * affiche la page finance lors du clique sur le bouton de la barre de navigation
     * @param event corresponds au bouton sur la barre de navigation
     */
    @FXML
    private void affichePageFinance(ActionEvent event){


        Button button = (Button) event.getSource();
        Stage fenetre = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("finance.fxml"));
        fxmlLoader.setController(this);
        Parent nouvelleScene = null;
        fenetre.setOnCloseRequest((WindowEvent t) -> this.exitAction());

        try {
            nouvelleScene = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fenetre.setScene(new Scene(nouvelleScene));
        fenetre.setResizable(false);
        fenetre.show();
        initialiseFinance();
        labelFilArianne.setText("Vos évènements > " + this.controleur.getEvenementCourant().getNom() + " > Finances");



    }

    private TreeItem<Transaction> root = new TreeItem<Transaction>(new Transaction("test",5,"tesssssssst"));
    private boolean isCreer = false;
    public boolean datePickerIsEmpty(DatePicker dp){
        return dp.getValue()==null;
    }

    /**
     * permet de recuperer l'ensemble des gains de l'évènement courant et de l'afficher dans la page finance
     */
    public void recuperationGain(){
        ArrayList<Transaction> tr = this.controleur.getDataRevenu();
        for (int i = 1; i < tr.size(); i++){
            ajouterRevenu(tr.get(i).getLibelle(),""+tr.get(i).getMontant(),tr.get(i).getDate());
        }
    }

    /**
     * permet d'afficher le slider avec representant le budget prévisionnel de l'évènement ainsi que le pourcentage du budget dépensé
     * ou si le budget n'est pas défini un message indicatif
     * @param sl slider représentant le budget
     * @param labelErreur message devant être affiché si le budget n'a pas encore était défini
     * @param labelBudget message qui est mis à jour le label initial afin de rajouter le pourcentage du budget deja dépensé
     */
    public void budgetIsDefine(Slider sl, Label labelErreur,Label labelBudget){
        if(this.controleur.getDataRevenu().get(0).getMontant()!=0){
            labelErreur.setVisible(false);
            sl.setVisible(true);
            labelBudget.setText("Budget prévisionnel : "+(double) Math.round(Double.parseDouble(this.controleur.getDepense())*100/this.controleur.getDataRevenu().get(0).getMontant()*100)/100+"% utilisés");
            sl.setMin(0.0);
            sl.setMax(this.controleur.getDataRevenu().get(0).getMontant());
            if (this.controleur.getDepense()==null){
                sl.setValue(0.0);
            }else {
                sl.setValue(Double.parseDouble(this.controleur.getDepense()));
            }
        }else {
            labelErreur.setVisible(true);
            sl.setVisible(false);
        }
    }

    /**
     * permet de recuperer l'ensemble des dépenses de l'évènement courant et de l'afficher dans la page finance
     */
    public void recuperationDepense(){
        ArrayList<Transaction> tr = this.controleur.getDataDepense();
        for (int i = 0; i < tr.size(); i++){
            ajouterDepense(tr.get(i).getLibelle(),""+tr.get(i).getMontant(),tr.get(i).getDate());
        }
    }

    /**
     * permet d'initialiser l'affichage de la page finance en mettant à jour la valeur du solde, des dépenses tot, des gains tot, du budget
     * ainsi que l'affichage des tableaux de gain et de perte
     */
    public void initialiseFinance(){
        lSolde.setText(this.controleur.getSolde()+" €");
        lDepensesTot.setText(this.controleur.getDepense()+" €");
        lRevenuTot.setText(this.controleur.getRevenu()+" €");

        sBalance.setDisable(true);

        recuperationGain();
        recuperationDepense();
        budgetFinance();
        }

    /**
     * permet de mettre à jour le bouton et le textfield du budget
     * si aucun budget entrer alors on peut ajouter une valeur valider
     * sinon on a un text field disable permettant uniquement l'affichage avec un bouton permettant cependant de revenir à la première condition en modifiant le champ entré
     */
    public void budgetFinance() {
            budgetIsDefine(sBalance, lMessageFinance,lBudgetFinance);
            if (this.controleur.getDataRevenu().get(0).getMontant() == 0) {
                tfBudget.setDisable(false);
                bBudget.setText("Valider");
            } else {
                tfBudget.setDisable(true);
                bBudget.setText("Modifier");
                tfBudget.setText(""+this.controleur.getDataRevenu().get(0).getMontant());
            }
        }

    /**
     * lors du clic du bouton relié au budget permet soit de valider le champ saisi, soit de modifier le champ deja entrer
     */
    public void onActionbBudget(){
            if (this.controleur.getDataRevenu().get(0).getMontant()!=0){
                tfBudget.setDisable(false);
                bBudget.setText("Valider");
                this.controleur.getDataRevenu().get(0).setMontant(0);
            }else {
                if(verifMontant(tfBudget.getText(),tfBudget)){
                    this.controleur.getDataRevenu().get(0).setMontant(Double.parseDouble(tfBudget.getText()));
                    budgetFinance();
                }
            }
        }

    /**
     * permet de savoir si le montant est bien de type double (, et . possible) et si la valeur est bien superieur à 0
     * si ces conditions sont verifier alors la fonction renvoie un boolean true
     * sinon la fonction renvoie false et le champ du texte field entrer est encadré en rouge
     * @param montant
     * @param tfMontantRevenu
     * @return
     */
    public boolean verifMontant(String montant, TextField tfMontantRevenu){
        boolean verif = true;
        int compt = 0;
        if (montant.isEmpty()){
            verif = false;
        }
        for (int i = 0; i<montant.length();i++){
            if (montant.charAt(i) == ('.' | ',')){
                compt++;
            } else if (montant.charAt(i) != ' ' & !Character.isDigit(montant.charAt(i))) {
                verif = false;
            }
        }
        if (compt > 1){
            verif = false;
        }
        markTextFieldErrorStatus(tfMontantRevenu,verif);
        return verif;
    }

    /**
     * Cette fonction permet de créer une ligne dans un tableau de label de revenus.
     * Cette ligne comporte un premier label motif, un deuxième, le montant et un dernier la date.
     * La ligne est ajoutée en toute première ligne de ce tableau.
     * @param motif
     * @param montant
     * @param date
     */
    public void ajouterRevenu(String motif, String montant, String date){
        HBox hBox = new HBox();
        Label lmotif = new Label();
        Label lmontant = new Label();
        Label ldate = new Label();

        lmotif.setText(motif);
        lmontant.setText(montant + " €");
        ldate.setText(date);

        hBox.setPrefWidth(600);
        lmotif.setPrefWidth(300);
        lmotif.setAlignment(Pos.CENTER);
        lmotif.setPadding(new Insets(0, 50,0,50));
        lmontant.setPrefWidth(300);
        lmontant.setAlignment(Pos.CENTER);
        lmontant.setPadding(new Insets(0,50,0,50));
        ldate.setPrefWidth(300);
        ldate.setAlignment(Pos.CENTER);
        ldate.setPadding(new Insets(0,50,0,50));

        hBox.getChildren().addAll(lmotif,lmontant,ldate);
        vbRevenu.getChildren().add(1,hBox);
    }
    /**
     * Cette fonction permet de créer une ligne dans un tableau de label de dépenses.
     * Cette ligne comporte un premier label motif, un deuxième, le montant et un dernier la date.
     * La ligne est ajoutée en toute première ligne de ce tableau.
     * @param motif
     * @param montant
     * @param date
     */
    public void ajouterDepense(String motif, String montant, String date){
        HBox hBox = new HBox();
        Label lmotif = new Label();
        Label lmontant = new Label();
        Label ldate = new Label();

        lmotif.setText(motif);
        lmontant.setText(montant + " €");
        ldate.setText(date);

        hBox.setPrefWidth(600);

        lmotif.setPrefWidth(300);
        lmotif.setAlignment(Pos.CENTER);
        lmotif.setPadding(new Insets(0, 50,0,50));

        lmontant.setPrefWidth(300);
        lmontant.setAlignment(Pos.CENTER);
        lmontant.setPadding(new Insets(0,50,0,50));

        ldate.setPrefWidth(300);
        ldate.setAlignment(Pos.CENTER);
        ldate.setPadding(new Insets(0,50,0,50));

        hBox.getChildren().addAll(lmotif,lmontant,ldate);
        vbDepense.getChildren().add(1,hBox);
    }

    /**
     * lors du clic du bouton ajouter un revenu permet de vérifier que tous les champs de liés à l'ajout d'un revenu sont remplis avec le bon format de data
     * si c'est le cas alors MAJ de tous les textField correspondant ainsi que l'affichage dans le tableau de revenus du nouveau revenu.
     * sinon les inputs des datas qui n'ont pas était verifier sont encadrées en rouge
     */
    public void onActionBAjouterRevenu(){
        double montant = 0;
        Boolean valide = validateNonEmptyTextField(tfMotifRevenu);
        valide &= !datePickerIsEmpty(dpDateRevenu);
        valide &= verifMontant(tfMontantRevenu.getText(), tfMontantRevenu);

        if (valide){
            montant = Double.parseDouble(tfMontantRevenu.getText());
            this.controleur.setNewRevenu(tfMotifRevenu.getText(),montant,dpDateRevenu.getValue().toString());
            lRevenuTot.setText(this.controleur.getRevenu()+" €");
            lSolde.setText(this.controleur.getSolde() + " €");
            ajouterRevenu(tfMotifRevenu.getText(),tfMontantRevenu.getText(),dpDateRevenu.getValue().toString());

            tfMontantRevenu.clear();
            tfMotifRevenu.clear();
            dpDateRevenu.setValue(null);
        }
    }
    /**
     * Lors du clic du bouton ajouter un revenu permet de vérifier que tous les champs de liés à l'ajout d'une dépense sont remplis avec le bon format de data
     * si c'est le cas alors MAJ de tous les textField correspondant ainsi que l'affichage dans le tableau de dépenses de la nouvelle dépense.
     * sinon les inputs des datas qui n'ont pas était verifier sont encadrées en rouge
     */
    public void onActionAjouterDepense(){
        boolean valide = true;
        double montant = 0;
        valide &= validateNonEmptyTextField(tfMotifDepense);
        valide &= !datePickerIsEmpty(dpDateDepense);
        valide &= verifMontant(tfMontantDepense.getText(),tfMontantDepense);

        if (valide){
            montant = Double.parseDouble(tfMontantDepense.getText());
            this.controleur.setNewDepense(tfMotifDepense.getText(),montant,dpDateDepense.getValue().toString());
            lDepensesTot.setText(this.controleur.getDepense()+" €");
            lSolde.setText(this.controleur.getSolde()+ " €");
            ajouterDepense(tfMotifDepense.getText(),tfMontantDepense.getText(),dpDateDepense.getValue().toString());

            tfMontantDepense.clear();
            tfMotifDepense.clear();
            dpDateDepense.setValue(null);
            budgetFinance();
        }
    }
}
