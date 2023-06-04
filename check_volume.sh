#!/bin/sh

echo "Start initial email result debugging script"
echo "Running containers:"
docker ps

CONTAINER_NAME="dashboard-springboot-docker-container-1"
MAX_VOLUME_SIZE="20" # Maximum volume size in kilobytes
EMAIL_SENDER="abdaouinessrine92@gmail.com" # Sender email address
EMAIL_RECIPIENT="mraihiamin@gmail.com" # Destination email address
SMTP_SERVER="sendmail" # Update to the name of the sendmail service
SMTP_PORT="1025" # Port of the sendmail service
LOG_FILE="/var/log/email_log.txt"

send_email_notification() {
  echo -e "From: $EMAIL_SENDER To: $EMAIL_RECIPIENT Subject: $1\n\n$2" | nc -w 1 $SMTP_SERVER $SMTP_PORT
}

send_initial_email() {
  echo "Sending initial email notification..."
  send_email_notification "Container $CONTAINER_NAME started." "This is the email body."
  if [ $? -eq 0 ]; then
    echo "Initial email notification sent successfully."
  else
    echo "Failed to send initial email notification."
  fi
}

# Send initial email notification
send_initial_email
