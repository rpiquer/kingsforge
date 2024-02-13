function moreItems(element){
    debugger;
var contenedor = document.getElementsByClassName("variable-quantity")[0];
element.innerHTML = parseInt(element.innerHTML) +1;
}

function lessItems(){
contenedor.innerHTML -= 1;
}