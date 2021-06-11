import java.util.*;


public class Usuario {
  public Sugerencia sugerenciaDiaria;

  public Sugerencia getSugerenciaDiaria(){
    return sugerenciaDiaria;
  }
}

public class Sugerencia {
  public List<atuendo> atuendos;
}
//2
public class AsesorDeImagen implements GeneradorSugerencias {
  private Sugerencia listaAtuendos;
  RepositorioUsuarios repoUsuarios;

  void calcularSugerenciasDiarias() {
    repoUsuarios.getUsuarios().forEach(usuario -> usuario.calcularSugerenciaDiaria());
  }
}

class RepositorioUsuarios{//podria ser singleton
  public List<Usuario> usuarios;

  public List<Usuario> getUsuarios(){
    return usuarios;
  }
}

public class Usuario {
  public Sugerencia sugerenciaDiaria;

  public Sugerencia getSugerenciaDiaria(){
    return sugerenciaDiaria;
  }

  public void calcularSugerencia() {
  }
  public void calcularSugerenciaDiaria() {
    this.sugerenciaDiaria = calcularSugerencia();
  }
}


//3
enum AlertaMeteorologica {
  TORMENTA,
  GRANIZO
}

public class RepositorioAlertas {
  List<AlertaMeteorologica> ultimasAlertas = new ArrayList<>();

  public void actualizarAlertas() {
    this.ultimasAlertas = new ServicioMeteorologicoAccuWeather().getAlertasMeteorologicas();
  }

}

//4
public final class AccuWeatherAPI {

  public final List<Map<String, Object>> getWeather(String ciudad) {
    return Arrays.asList(new HashMap<String, Object>(){{
      put("DateTime", "2019-05-03T01:00:00-03:00");
      put("EpochDateTime", 1556856000);
      put("WeatherIcon", 33);
      put("IconPhrase", "Clear");
      put("IsDaylight", false);
      put("PrecipitationProbability", 0);
      put("MobileLink", "http://m.accuweather.com/en/ar/villa-vil/7984/");
      put("Link", "http://www.accuweather.com/en/ar/villa-vil/7984");
      put("Temperature", new HashMap<String, Object>(){{
        put("Value", 57);
        put("Unit", "F");
        put("UnitType", 18);
      }});
    }});
  }
}


class ServicioMeteorologicoAccuWeather implements ServicioMeteorologico {

  List<AlertaMeteorologica> getAlertasMeteorologicas() {
    AccuWeatherAPI apiClima = new AccuWeatherAPI();
    Map<String, Object> alertas = apiClima.getWeather("Buenos Aires");
    return convertirAlertas(alertas.get("CurrentAlerts"));
  }

  List<AlertaMeteorologica> convertirAlertas(List<String> alertas) {
    List<AlertaMeteorologica> listaAlertas = new ArrayList<>();
    alertas.forEach(alerta -> convertir(alerta,listaAlertas));
    return listaAlertas;
  }

  void convertir(String alerta,List<AlertaMeteorologica> listaAlertas) {

    if(alerta.equals("STORM")){
      listaAlertas.add(AlertaMeteorologica.TORMENTA);
    }
    if(alerta.equals("HAIL")){
      listaAlertas.add(AlertaMeteorologica.GRANIZO);
    }
  }

}


//5
public class RepositorioAlertas {
  List<AlertaMeteorologica> ultimasAlertas = new ArrayList<>();
  RepositorioUsuarios repoUsuarios;

  public void actualizarAlertas() {
    this.ultimasAlertas = new ServicioMeteorologicoAccuWeather().getAlertasMeteorologicas();
    this.ejecutar(this.ultimasAlertas);
  }

  public void ejecutar(List<AlertaMeteorologica> alertas){
    repoUsuarios.getUsuarios().forEach(usuario -> usuario.ejecutarAccionesAlertas(alertas));
  }
}

public class Usuario {
  Sugerencia sugerenciaDiaria;
  List<Accion> acciones = new ArrayList<>();
  String email;

  Sugerencia getSugerenciaDiaria(){
    return sugerenciaDiaria;
  }

  public void agregarAccion(Accion accion){
    this.acciones.add(accion);
  }

  public void removerAccion(Accion accion){
    this.acciones.remove(accion);
  }

  public void ejecutarAccionesAlertas(List<AlertaMeteorologica> alertas){
    //El type test lo podemos resolver con las estrategias que ya conocemos
    if(alertas.contains(AlertaMeteorologica.TORMENTA)){
      acciones.forEach(accion -> accion.alertaTormenta(this));
    }else if(alertas.contains(AlertaMeteorologica.GRANIZO)) {
      acciones.forEach(accion -> accion.alertaGranizo(this));
    }
  }

  public String getEmail(){
    return email;
  }
}

public interface Accion {
  public void alertaTormenta(Usuario usuario);
  public void alertaGranizo(Usuario usuario);
}

class NotificadorAlertas implements Accion {
  @Override
  public void alertaTormenta(Usuario usuario) {
    new Notificador().notificar(AlertaMeteorologica.TORMENTA.getMensaje());
  }

  @Override
  public void alertaGranizo(Usuario usuario) {
    new Notificador().notificar(AlertaMeteorologica.GRANIZO.getMensaje());
  }
}

class NotificadorMail implements Accion {
  @Override
  public void alertaTormenta(Usuario usuario) {
    new MailSender().send(usuario.getEmail(),AlertaMeteorologica.TORMENTA.getMensaje());
  }

  @Override
  public void alertaGranizo(Usuario usuario) {
    new MailSender().send(usuario.getEmail(),AlertaMeteorologica.GRANIZO.getMensaje());;
  }
}

enum AlertaMeteorologica{
  TORMENTA(){
    getMensaje(){
      return "Se aproxima tormenta, salir con paraguas";
    }
    },
  GRANIZO(){
    getMensaje(){
      return "Se aproxima granizo,no salir con auto";
    }
  }
  }