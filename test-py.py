import subprocess
import time

def get_container_statuses():
    command = 'docker ps -a --format "{{.Names}} - {{.Status}}" | awk \'/calculate-size|springboot-docker-container|mysql-standalone/ {print}\''
    output = subprocess.check_output(command, shell=True)
    return output.decode('utf-8')

if __name__ == '__main__':
    interval_s = 60
    last_time = time.time()

    while True:
        current_time = time.time()
        diff = current_time - last_time

        if diff >= interval_s:
            container_statuses = get_container_statuses()
            print(container_statuses, flush=True)
            last_time = current_time

        time.sleep(1)  # Sleep for 1 second before checking the interval again
