package absence.Controllers;

import absence.Dao.EtudiantDAO;
import absence.Modele.Etudiant;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class EtudiantController {
    private static EtudiantDAO etudiantDAO = new EtudiantDAO();
    public boolean importerEtudiantsDepuisExcel(String fichierPath) {
        try (FileInputStream fis = new FileInputStream(new File(fichierPath))) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // On suppose que les données sont sur la première feuille

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Ignorer la première ligne (en-têtes)

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String cne = getStringCellValue(row.getCell(0));
                String nom = getStringCellValue(row.getCell(1));
                String prenom = getStringCellValue(row.getCell(2));
                String email = getStringCellValue(row.getCell(3));
                String telephone = getStringCellValue(row.getCell(4));
                String sexe = getStringCellValue(row.getCell(5));
                int idClasse = (int) row.getCell(6).getNumericCellValue(); // Supposons qu'il est toujours numérique.

                // Créer l'objet Etudiant et l'ajouter à la base de données
                Etudiant etudiant = new Etudiant(cne, nom, prenom, email, telephone, sexe, idClasse);
                etudiantDAO.ajouterEtudiant(etudiant);
            }
            workbook.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private String getStringCellValue(Cell cell) {
        if (cell == null) return ""; // Cellule vide
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue()); // Convertit en entier puis en chaîne.
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }


    public static void main(String[] args) {
        EtudiantController cont=new EtudiantController();
        cont.importerEtudiantsDepuisExcel("C:/Users/asus/Desktop/testbd.xlsx");
    }

    }


