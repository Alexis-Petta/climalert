package ar.edu.utn.dsi.climalert.service;

import ar.edu.utn.dsi.climalert.config.ClimalertProperties;
import ar.edu.utn.dsi.climalert.domain.WeatherAlert;
import ar.edu.utn.dsi.climalert.domain.WeatherRecord;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailAlertNotifier implements AlertNotifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailAlertNotifier.class);

    private final JavaMailSender mailSender;
    private final ClimalertProperties properties;

    public EmailAlertNotifier(JavaMailSender mailSender, ClimalertProperties properties) {
        this.mailSender = mailSender;
        this.properties = properties;
    }

    @Override
    public NotificationResult notify(WeatherAlert alert, WeatherRecord record) {
        List<String> recipients = properties.getNotification().getRecipients();

        if (!properties.getNotification().isEnabled()) {
            String detail = "Envio de email deshabilitado. Destinatarios configurados: " + String.join(", ", recipients);
            LOGGER.warn(detail);
            return NotificationResult.skipped(detail);
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipients.toArray(String[]::new));
            message.setSubject("[Climalert] Alerta meteorologica en " + record.getResolvedLocation());
            message.setText(buildBody(record));
            mailSender.send(message);

            String detail = "Email enviado a " + String.join(", ", recipients);
            LOGGER.info(detail);
            return NotificationResult.sent(detail);
        } catch (RuntimeException exception) {
            String detail = "Error enviando email: " + exception.getMessage();
            LOGGER.error(detail, exception);
            return NotificationResult.failed(detail);
        }
    }

    private String buildBody(WeatherRecord record) {
        return "Se detectaron condiciones climaticas criticas.\n\n"
                + "Detalle completo del clima:\n"
                + "- Ubicacion solicitada: " + record.getRequestedLocation() + "\n"
                + "- Ubicacion resuelta: " + record.getResolvedLocation() + "\n"
                + "- Fecha/hora observada: " + record.getObservedAt() + "\n"
                + "- Temperatura: " + record.getTemperatureC() + " °C\n"
                + "- Humedad: " + record.getHumidity() + "%\n"
                + "- Sensacion termica: " + record.getFeelsLikeC() + " °C\n"
                + "- Viento: " + record.getWindKph() + " km/h\n"
                + "- Condicion: " + record.getConditionText() + "\n\n"
                + "Payload original WeatherAPI:\n"
                + record.getRawPayload();
    }
}
