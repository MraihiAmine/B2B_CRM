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
EMAIL_SUBJECT = 'Container dashboard-springboot-docker-container-1 started.'
EMAIL_BODY = 'This is the email body.'

def send_email():
    # Create a multipart message
    message = MIMEMultipart()
    message['From'] = EMAIL_SENDER
    message['To'] = EMAIL_RECIPIENT
    message['Subject'] = EMAIL_SUBJECT

    # Attach the body to the message
    message.attach(MIMEText(EMAIL_BODY, 'plain'))

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

# Call the send_email function
send_email()
