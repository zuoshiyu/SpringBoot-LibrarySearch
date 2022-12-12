$(document).ready(function() {
	// modify table behavior
	$("table").DataTable({
		'order': [[4, 'asc']],
		'aoColumnDefs': [{
			'bSortable': false,
			'aTargets': [-1]
		}]
	});
});
function loadNavigator() {
	// load navigator element
	const navigator = document.createElement("template");
	navigator.innerHTML = `
	    <ul class="nav nav-pills nav-stacked">
	
	      <li class="active">
	        <a href="/simplesearch">Home</a>
	      </li>
	
	      <li class="dropdown">
	        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	          Searches <span class="caret"></span>
	        </a>
	        <ul class="dropdown-menu">
	          <li>
	            <a href="/simplesearch">Simple Search</a>
	          </li>
	          <li>
	            <a href="/advancedsearch">Advanced Search</a>
	          </li>
	          <li>
	            <a href="/textsearch">Text Search</a>
	          </li>
	          <li>
	            <a href="/hotwordsearch">Hotword Search</a>
	          </li>
	          <li>
	            <a href="/oversearch">Over Search</a>
	          </li>
	          <li>
	            <a href="/specifiedsearch">Specified Search</a>
	          </li>
	        </ul>
	      </li>
	
	      <li class="dropdown">
	        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	          Admin view <span class="caret"></span>
	        </a>
	        <ul class="dropdown-menu">
	          <li>
	            <a href="/showPhysicalBooks">View Physical Books</a>
	          </li>
	          <li>
	            <a href="/showDigitalBooks">View Digital Books</a>
	          </li>
	          <li>
	            <a href="/showUsers">View Users</a>
	          </li>
	        </ul>
	      </li>
	
	      <li class="dropdown">
	        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	          Admin add <span class="caret"></span>
	        </a>
	        <ul class="dropdown-menu">
	          <li>
	            <a href="/addPhysicalBookForm">Add Physical Book</a>
	          </li>
	          <li>
	            <a href="/addDigitalBookForm">Add Digital Book</a>
	          </li>
	          <li>
	            <a href="/addUserForm">Add User</a>
	          </li>
	        </ul>
	      </li>
	      
	      <li>
	        <a href="/logout">Log out</a>
	      </li>
	    </ul>
	`;
	document.getElementById('navigator').appendChild(navigator.content);
}
