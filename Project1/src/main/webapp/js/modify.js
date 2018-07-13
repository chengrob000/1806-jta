function addAttachment() {
	let del = document.createTextNode("X");
	let delButton = document.createElement('button');
	let uuid = uuidv4();
	let addAttachment = document.createElement('input');
	
	addAttachment.setAttribute("type", "file");
	addAttachment.setAttribute("id", uuid);
	addAttachment.setAttribute("accept", ".pdf,.png,.jpeg,.txt,.doc,.msg");
	
	delButton.setAttribute("type", "button");
	delButton.setAttribute("onclick", "removeRow('" + uuid + "')");
	delButton.appendChild(del);
	
	let td1 = document.createElement('td');
	let td2 = document.createElement('td');
	td1.appendChild(addAttachment);
	td2.appendChild(delButton);
	let row = document.createElement('tr');
	row.setAttribute("id", uuid);
	row.appendChild(td1);
	row.appendChild(td2);
	document.getElementById("attachmentTable").appendChild(row);
}

function removeRow(x) {
	document.getElementById(x).remove();
}

function currentDate() {
	var date = new Date();
	var dd = date.getDate();
	var mm = date.getMonth();
	var yyyy = date.getYear();
	
	if (dd < 10) {
		dd = '0' + dd;
	}
	
	if (mm < 10 ) {
		mm = '0' + mm;
	}
	
	var currentDate = yyyy + '-' + mm +'-' + dd;
	document.getElementById('currentDate').value = currentDate();
}

document.addEventListener('DOMContentLoaded', function() {
	currentDate();
}, false);

window.onload = function() {
	currentDate();
};