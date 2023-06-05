import subprocess
import time
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

# SMTP server configuration
SMTP_SERVER = 'smtp.gmail.com'
SMTP_PORT = 587
SMTP_USERNAME = 'abdaouinessrine92@gmail.com'
SMTP_PASSWORD = 'scmswinstpxmzhuj'

# Email details
EMAIL_SENDER = 'abdaouinessrine92@gmail.com'
EMAIL_RECIPIENT = 'mraihiamin@gmail.com'
EMAIL_SUBJECT = 'Stopped containers'
EMAIL_BODY = 'This is the email body.'


def send_email(subject, body):
    # Create a multipart message
    message = MIMEMultipart()
    message['From'] = EMAIL_SENDER
    message['To'] = EMAIL_RECIPIENT
    message['Subject'] = subject

    # Attach the body to the message
    message.attach(MIMEText(body, 'plain'))

    try:
        # Create a secure connection to the SMTP server
        server = smtplib.SMTP(SMTP_SERVER, SMTP_PORT)
        server.starttls()
        server.login(SMTP_USERNAME, SMTP_PASSWORD)

        # Send the email
        server.sendmail(EMAIL_SENDER, EMAIL_RECIPIENT, message.as_string())
        print('Email sent successfully!')

    except Exception as e:
        print('Failed to send email:', str(e))

    finally:
        # Close the connection
        server.quit()


def get_container_statuses():
    command = 'docker ps -a --format "{{.Names}} - {{.Status}}" | awk \'/calculate-size|springboot-docker-container|mysql-standalone/ {print}\''
    output = subprocess.check_output(command, shell=True)
    return output.decode('utf-8')


def get_stopped_containers(container_statuses):
    stopped_containers = ''
    lines = container_statuses.splitlines()
    for line in lines:
        if "Exited" in line:
            stopped_containers = stopped_containers + line + '\n'
    return stopped_containers


if __name__ == '__main__':
    interval_s = 60
    last_time = time.time()

    while True:
        current_time = time.time()
        diff = current_time - last_time

        if diff >= interval_s:
            container_statuses = get_container_statuses()
            stopped_containers = get_stopped_containers(container_statuses)

            print('container_statuses', flush=True)
            print(container_statuses, flush=True)
            print('container stopped', flush=True)
            print(stopped_containers, flush=True)

            if(stopped_containers != ''):
                print('Entered to the mailing function', flush=True)
                //send_email(EMAIL_SUBJECT, stopped_containers)
                stopped_containers = ''

            last_time = current_time
        time.sleep(1)  # Sleep for 1 second before checking the interval again
