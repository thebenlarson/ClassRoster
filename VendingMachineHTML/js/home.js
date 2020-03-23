let money = 0.00;
let itemLookUp = [];
let selectedItemId = 0;
let change = null;
let purchase = false;

$(document).ready(function () {
    resetMoney();
    loadItems();
});

function loadItems() {
    clearItems();
    const itemsContainer = $("#itemsContainer");

    $.ajax ({
        type: 'GET',
        url: 'http://tsg-vending.herokuapp.com/items',
        success: function (data, status) {
            let card = '';
            //console.log(data);
            $.each(data, function (index, item) {
                let myIndex = index + 1;
                let price = item.price;
                let name = item.name;
                let id = item.id;
                let quantity = item.quantity;
                item.myIndex = myIndex;

                itemLookUp[id] = item;

                //add row condition
                if (myIndex % 3 == 1){
                    card += '<div class="row item-row">';
                }

                card += '<div class="col-md-4"><div class="card border border-secondary" onclick="loadItem(' + id + ')"><div class="card-body">';
                card += '<div class="text-left" style="margin-left: 0">' + myIndex + '</div>';
                card += '<h5 class="card-title">' + name + '</h5>';
                card += '<p class="card-text">$' + price.toFixed(2) + '</p>';
                card += '</div><div class="card-footer"><div class="row"><div class="col-md-8">Quantity Left:</div>';
                card += '<div class="col-md-4">' + quantity + '</div>';
                card += '</div></div></div></div>';

                if (myIndex % 3 == 0 || data.length == myIndex){
                    card += '</div>';
                }
            });
            itemsContainer.append(card);
        },
        error: function() {
            $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error connecting to web service. Please try again later.'));
        }
    });
}

function clearItems(){
    $("#itemsContainer").empty();
}

function loadItem(index){
    //put the corresponding myIndex into the textarea/input
    clearFieldsAfterPurchase();

    selectedItemId = index;
    $("#itemChoice").val(itemLookUp[index].myIndex);
}

function makePurchase() {
    //format money and get things
    $("#change").val('');

    if ($("#itemChoice").val() == ''){
        $('#messages').val("Please make a selection!");
        return;
    }

    const myMoney = money.toFixed(2);
    const url = 'http://tsg-vending.herokuapp.com/money/' + myMoney + '/item/' + selectedItemId;

    $.ajax({
            type: 'POST',
            url: url,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function(data, status) {
                // clear errorMessages
                saveChange(data);
                loadItems();
                purchase = true;

                let myMessage = "Thank you!";
                $('#messages').val(myMessage);
                $("#change").val(change);

                resetMoney();
                $("#itemChoice").val('');
            },
            error: function(e) {
                loadItems();
                $('#messages').val(e.responseJSON.message);
            }
        });

}


function resetMoney(){
    money = 0.00;
    updateMoney();
}

function addMoney(amount){
    clearFieldsAfterPurchase();
    money += amount;
    updateMoney();
}

function updateMoney(){
    $("#moneyInput").val("$" + money.toFixed(2));
}

function saveChange(obj) {
    change = '';

    for (const [key, value] of Object.entries(obj)) {
      if (value != 0){
        change += value + " " + formatCoins(key, value) + ", "; 
      }
    }
    
    change = change.substring(0, change.length - 2);
}

function formatCoins(key, value){
    if (value == 1) {
        if (key == "pennies"){
            return "penny";
        } else {
            return key.substring(0, key.length - 1);
        }
    } else {
        return key;
    }
}

function clearInputs(){
    //$("#change").val('');
    $("#messages").val('');
    $("#itemChoice").val('');
}

function changeReturn(){
    clearInputs();
    loadItems();

    if (money == 0){
        $("#change").val('');
        return;
    }

    saveChange(calculateChange());
    $("#change").val(change);
    resetMoney();
}

function calculateChange(){
    //for change return
    let quarters = 0, dimes = 0, nickels = 0, pennies = 0;
    quarters = Math.floor(money / 0.25);
    money -= (quarters * 0.25);
    dimes = Math.floor(money / 0.10);
    money -= (dimes * 0.10);
    nickels = Math.floor(money / 0.05);
    money -= (nickels * 0.05);
    pennies = Math.floor(money / 0.01);

    return {quarters : quarters, dimes : dimes, nickels : nickels, pennies : pennies};
}

function clearFieldsAfterPurchase(){
    if (purchase){
        clearInputs();
        $("#change").val('');
        purchase = false;
    }
}
