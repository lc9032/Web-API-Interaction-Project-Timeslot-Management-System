# API Client for Creating Timeslots

This Python script interacts with the server to create timeslots for users.

## Requirements

- Python 3.X
- `requests` library
- `datetime` library

## Installation

Before using the script, make sure you have the required Python libraries installed (The datetime library is part of the Python standard library). 
You can install them using `pip`.

1. Clone this repository or download the `requirements.txt` script.
1. Open a terminal/command prompt.
2. Navigate to the directory containing `requirements.txt`.

Run the following command to install the required libraries:
```sh
pip install -r requirements.txt
```

## Usage

1. Clone this repository or download the `client.py` script.
2. Open a terminal/command prompt.
3. Navigate to the directory containing `client.py`.

### Create Timeslot

To create a timeslot for a user, use the following command:

```sh
python client.py <person_id> <project_id> <duration_in_minutes> <description>
```

Replace <person_id>, <project_id>, <duration_in_minutes> and <description> with appropriate values.

- <person_id>: ID of the user for whom the timeslot is being created.
- <project_id>: ID of the project
- <duration_in_minutes>: Duration of the timeslot in minutes.
- <description>: Description of the timeslot.

Example:
```sh
python client.py 1 3 60 "Important Meeting"
```

### Note
- The script calculates the end time of the timeslot based on the current time and the provided duration.
- The script uses the API base URL defined in the BASE_URL variable within the script. Update this URL if needed.