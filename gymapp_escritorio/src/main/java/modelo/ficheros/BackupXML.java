package modelo.ficheros;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modelo.entity.Cliente;
import modelo.entity.Historico;
import modelo.exceptions.FileException;

public class BackupXML {

    public static final String BACKUP_XML = "backup_clientes.xml";
    
    
    public static void writeXMLFile(List<Cliente> clientes) throws FileException, TransformerException, ParserConfigurationException {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("clientes");
            document.appendChild(root);

            System.out.println("📦 Total de clientes: " + clientes.size());

            
            for (Cliente cliente : clientes) {
                Element clienteElem = document.createElement("cliente");
                clienteElem.setAttribute("id", cliente.getId());

                //Datos básicos del cliente
                Element nombre = document.createElement("nombre");
                nombre.appendChild(document.createTextNode(cliente.getNombre() != null ? cliente.getNombre() : ""));
                clienteElem.appendChild(nombre);

                Element apellido1 = document.createElement("apellido1");
                apellido1.appendChild(document.createTextNode(cliente.getApellido1() != null ? cliente.getApellido1() : ""));
                clienteElem.appendChild(apellido1);

                Element apellido2 = document.createElement("apellido2");
                apellido2.appendChild(document.createTextNode(cliente.getApellido2() != null ? cliente.getApellido2() : ""));
                clienteElem.appendChild(apellido2);

                Element email = document.createElement("email");
                email.appendChild(document.createTextNode(cliente.getEmail() != null ? cliente.getEmail() : ""));
                clienteElem.appendChild(email);
                
                Element password = document.createElement("password");
                password.appendChild(document.createTextNode(cliente.getPassword() != null ? cliente.getPassword() : ""));
                clienteElem.appendChild(password);

                Element nivel = document.createElement("nivel");
                nivel.appendChild(document.createTextNode(cliente.getNivel() != null ? cliente.getNivel() : ""));
                clienteElem.appendChild(nivel);

                //Añadir los históricos del cliente
                List<Historico> historicos = cliente.getHistoricos();
                if (historicos != null && !historicos.isEmpty()) {
                    Element historicosElem = document.createElement("historicos");

                    for (Historico historico : historicos) {
                        Element historicoElem = document.createElement("historico");
                        historicoElem.setAttribute("id", historico.getId());

                        Element hNombre = document.createElement("nombre");
                        hNombre.appendChild(document.createTextNode(historico.getNombre() != null ? historico.getNombre() : ""));
                        historicoElem.appendChild(hNombre);

                        Element hNivel = document.createElement("nivel");
                        hNivel.appendChild(document.createTextNode(historico.getNivel() != null ? historico.getNivel() : ""));
                        historicoElem.appendChild(hNivel);

                        Element tiempoTotal = document.createElement("tiempo_total");
                        tiempoTotal.appendChild(document.createTextNode(historico.getTiempoTotal() != null ? historico.getTiempoTotal() : ""));
                        historicoElem.appendChild(tiempoTotal);

                        Element tiempoPrevisto = document.createElement("tiempo_previsto");
                        tiempoPrevisto.appendChild(document.createTextNode(historico.getTiempoPrevisto() != null ? historico.getTiempoPrevisto() : ""));
                        historicoElem.appendChild(tiempoPrevisto);

                        Element fechaInicio = document.createElement("fecha_inicio");
                        fechaInicio.appendChild(document.createTextNode(historico.getFecha() != null ? historico.getFecha() : ""));
                        historicoElem.appendChild(fechaInicio);

                        Element porcentaje = document.createElement("porcentaje");
                        porcentaje.appendChild(document.createTextNode(historico.getEjerciciosCompletados() != null ? historico.getEjerciciosCompletados() : ""));
                        historicoElem.appendChild(porcentaje);

                        historicosElem.appendChild(historicoElem);
                    }
                    clienteElem.appendChild(historicosElem);
                }

                root.appendChild(clienteElem);
            }

            // Guardar XML en archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(BACKUP_XML));
            
            transformer.transform(source, result);

            System.out.println("Archivo XML generado correctamente: backup_clientes.xml");
        }
    
    
    public static List<Cliente> readXMLFile(String filePath)
            throws FileException, ParserConfigurationException, IOException, SAXException {

        List<Cliente> clientes = new ArrayList<>();

        // 1️⃣ Crear parser DOM
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(new File(filePath));
        document.getDocumentElement().normalize();

        // 2️⃣ Obtener todos los nodos <cliente>
        NodeList clienteNodes = document.getElementsByTagName("cliente");

        for (int i = 0; i < clienteNodes.getLength(); i++) {
            Node clienteNode = clienteNodes.item(i);
            if (clienteNode.getNodeType() == Node.ELEMENT_NODE) {
                Element clienteElement = (Element) clienteNode;

                // Datos del cliente
                String id = clienteElement.getAttribute("id");
                String nombre = getTagValue("nombre", clienteElement);
                String apellido1 = getTagValue("apellido1", clienteElement);
                String apellido2 = getTagValue("apellido2", clienteElement);
                String fechaNacimiento = getTagValue("fecha_nacimiento", clienteElement);
                String email = getTagValue("email", clienteElement);
                String password = getTagValue("password", clienteElement);
                String nivel = getTagValue("nivel", clienteElement);

                // Lista de históricos
                List<Historico> historicos = new ArrayList<>();
                NodeList historicoNodes = clienteElement.getElementsByTagName("historico");

                for (int j = 0; j < historicoNodes.getLength(); j++) {
                    Node historicoNode = historicoNodes.item(j);
                    if (historicoNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element historicoElement = (Element) historicoNode;

                        Historico historico = new Historico(
                            historicoElement.getAttribute("id"),
                            getTagValue("nombre", historicoElement),
                            getTagValue("nivel", historicoElement),
                            getTagValue("tiempo_total", historicoElement),
                            getTagValue("tiempo_previsto", historicoElement),
                            getTagValue("fecha_inicio", historicoElement),
                            getTagValue("porcentaje", historicoElement)
                        );
                        historicos.add(historico);
                    }
                }

                // Crear el objeto Cliente
                Cliente cliente = new Cliente(
                    id, nombre, apellido1, apellido2,
                    fechaNacimiento, email, password, nivel, historicos
                );

                clientes.add(cliente);
            }
        }

        return clientes;
    }

    /**
     * Método auxiliar para obtener texto de una etiqueta XML
     */
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }

    }