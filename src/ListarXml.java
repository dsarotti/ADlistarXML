import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Usando el API de DocumentBuilder, recorre el archivo XML facilitado y muestra
 *  por pantalla el nombre de las etiquetas y el texto que contiene de esta forma:
 * {Etiqueta: texto}
 *
 * Si no tiene texto:
 * {Etiqueta}
 *
 * Crea un pdf mostrando el resultado y el código con una explicación de lo que realiza.
 */
public class ListarXml {
    //Ruta al fichero xml
    private static final String ruta =".\\src\\data.xml";
    public static void main(String[] args) throws Exception {
        Document doc = cargarXML(ruta);
        Node primerNodo = doc.getFirstChild();
        listar(primerNodo,0);
    }

    /**
     * Recibe un nodo y su indentación y muestra su nombre y su texto si es que lo 
     * tiene, después hace lo mismo con sus hijos de forma recursiva.
     * @param nodo un nodo.
     * @param indent El nivel de indentación actual.
     */
    public static void listar (Node nodo,int indent){
        //Obtener el texto del nodo actual
        String textoNodo = nodo.getFirstChild().getTextContent();
        //Muestra el texto indentado correspondiente al nodo actual
        if(!textoNodo.isBlank()){
            System.out.print(("{"+nodo.getNodeName()+": " + textoNodo + "}").indent(indent));
        }else{
            System.out.print(("{" + nodo.getNodeName() + "}").indent(indent));
        }

        //Siguiente iteración: obtiene los hijos de este nodo
        NodeList listaNodos = nodo.getChildNodes();

        //Recorre los hijos de este nodo con una llamada recursiva a este método
        for(int i = 0;i<listaNodos.getLength();i++){
            Node nodoActual = listaNodos.item(i);
            if (nodoActual.hasChildNodes()){
                listar(nodoActual,(indent+3));
            }
        }
    }

    /**
     * Recibe una ruta y devuelve el objeto Document correspondiente al documento xml
     * Si no se encuentra el documento devuelve un null
     * @param ruta la ruta al documento xml
     * @return un objeto Document o null si no existe
     */
    public static Document cargarXML(String ruta){
        Document doc=null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        FileInputStream fis = null;
        //Cargar el fichero xml
        try{
            //Se solicita a la factoría un nuevo DocumentBuilder
            db=dbf.newDocumentBuilder();
            //Se crea el flujo para obtener el documento
            fis = new FileInputStream(new File(ruta));
            //Se obtiene el contenido del documento a partir del flujo creado antes
            doc=db.parse(fis);
        }catch (IOException| ParserConfigurationException|SAXException e){
            System.out.println("Error al acceder al documento.");
            e.printStackTrace();
        }finally{
            try{
                fis.close();
            }catch (IOException | NullPointerException e){
                System.out.println("Error al cerrar el rescurso.");
                e.printStackTrace();
            }
        }
        return doc;
    }
}