import sys
import requests
from datetime import datetime, timedelta

BASE_URL = "http://localhost:8080"

def create_timeslot(person_id, project_id, duration, description):
    url = f"{BASE_URL}/timeSlot/create.json"

    start_time = datetime.now()
    end_time = start_time + duration

    payload = {
        "personId": person_id,
        "projectId": project_id,
        "date": start_time.strftime("%Y-%m-%d"),
        "startTime": start_time.strftime("%H:%M:%S"),
        "endTime": end_time.strftime("%H:%M:%S"),
        "description": description
    }

    response = requests.post(url, json=payload)

    if response.status_code == 200:
        print("successful")
    else:
        print("Error inserting timeslot:", response.text)

if __name__ == '__main__':
    if len(sys.argv) != 5:
        print("Usage: python client.py <person_id> <project_id> <duration> <description>")
        sys.exit(1)

    try:
        person_id = int(sys.argv[1])
        project_id = int(sys.argv[2])
        duration = int(sys.argv[3])
        description = sys.argv[4]
        duration = timedelta(minutes=duration)
    except ValueError:
        print("Invalid arguments. Usage: python client.py <person_id> <duration> <description>")
        sys.exit(1)

    create_timeslot(person_id, project_id, duration, description)