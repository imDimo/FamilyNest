let stompClient = null;

const mySenderId = "user-" + Math.random().toString(36).substring(2, 9);

let dayContainers = [];

function main() {
    addFormValidation();
    initEvents();
    let bttn = document.getElementById("announceButton");
    if (bttn) {
        bttn.addEventListener("click", sendAnnouncement);
    }

    getEvents();
    
    connect(); 
}

function addFormValidation() {
    let form = document.getElementById("create-event-form");
    form.addEventListener("submit", onFormSubmit);
}

function onFormSubmit(e) {

    let title = document.getElementById("title");
    let date = document.getElementById("date");

    if (title.value.trim().length == 0) {
        e.preventDefault();
        title.focus();
        return;
    }

    if (date.value.trim().length == 0) {
        e.preventDefault();
        date.focus();
        return;
    }
}

function initEvents() {
    for (let i = 1;; i++) {
        let dayContainer = document.getElementById(`day-${i}`);
        if (!dayContainer) {
            break;
        }
        
        dayContainers.push(dayContainer);
        dayContainer.addEventListener("click", toggleAddEventPopup);
    }

    document.getElementById("bttn-close-popup").addEventListener("click", toggleAddEventPopup);

    let date = new Date();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();

    getEvents(month, year);
}

function toggleAddEventPopup(e) {
    let currentDate = new Date();

    let year = currentDate.getFullYear();
    let month = currentDate.getMonth();
    let day = e.target.id.replace(/^\D+/g, '');

    let date = new Date(year, month, day);

    document.getElementById("date").value = date.toISOString().substring(0, 10);
    document.getElementById("create-event-popup").classList.toggle("show");
}

function sendAnnouncement() {
    let titleInput = document.getElementById("announcementTitle");
    let contentInput = document.getElementById("announcementContent");

    stompClient.publish({
        destination: "/app/announcement", 
        body: JSON.stringify({
            "title": titleInput.value,
            "content": contentInput.value,
            "senderId" : mySenderId
        })
    });

    titleInput.value = "";
    contentInput.value = "";
}

function connect() {
    let url = `ws://${location.host}/ws`;
    stompClient = new StompJs.Client({
        brokerURL: url,
        onConnect: () => {
            stompClient.subscribe("/topic/announcements", (msg) => {
                const data = JSON.parse(msg.body);
                console.log("Announcement received:", data); 
                if (data.senderId && data.senderId !== mySenderId) {
                    showAnnouncement(data);
                }
            });
        }
    });
    stompClient.activate();
}

function getEvents(month, year) {
    let url = `http://${location.host}`;
    fetch(`${url}/events/${month}/${year}`)
    .then(response => {
        if (!response.ok) {
            throw new Error("Could not connect to server");
        }
        return response.json();
    })
    .then(data => {
        addEventsToCalendar(data);
    })
    .catch(error => {
        console.error(error);
    });
}

function addEventsToCalendar(events) {
    let counter = 0;
    let list = document.getElementById("events-list");

    events.forEach(e => {
        let date = new Date(e.eventDate);

        if (counter < 8) {
            let elem = document.createElement("div");
            elem.classList.add("event-list-item")
            elem.insertAdjacentHTML("beforeend", `<p class="bold center-align">${e.title}</p><p>${e.description}</p><p>${date.toDateString()}, ${e.eventTime}</p>`);
            list.appendChild(elem);
        }

        counter++;
    });
}

window.onload = main;
