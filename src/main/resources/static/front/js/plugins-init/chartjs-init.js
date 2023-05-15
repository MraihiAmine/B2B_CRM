function generateChart(products, selectedProductId) {
  "use strict";

  //Team chart

  console.log("products", products);
  console.log("selectedProductId", selectedProductId);
  let productSelected = products.filter(e => {console.log("e", e); return e.id == selectedProductId})[0];
  productSelected = productSelected ? productSelected : products[0]
  console.log("productSelected", productSelected);
  const quarters = ["Q1", "Q2", "Q3", "Q4"];
  const quartersYears = [];
  const RRarray = [];
  console.log(productSelected);
  console.log(productSelected.customerRetentionRateEntities);

  for (let i = 0; i < productSelected.customerRetentionRateEntities.length; i++) {
    for (let j = 0; j < quarters.length; j++) {
      // console.log(productSelected.customerRetentionRateEntities[i]);
      quartersYears.push(
        productSelected.customerRetentionRateEntities[i].yearStatistic.year +
          " " +
          quarters[j]
      );
      switch (j) {
        case 0:
          RRarray.push(
            productSelected.customerRetentionRateEntities[i].retentionRateQ1
          );
          break;
        case 1:
          RRarray.push(
            productSelected.customerRetentionRateEntities[i].retentionRateQ2
          );
          break;
        case 2:
          RRarray.push(
            productSelected.customerRetentionRateEntities[i].retentionRateQ3
          );
          break;
        case 3:
          RRarray.push(
            productSelected.customerRetentionRateEntities[i].retentionRateQ4
          );
          break;

        default:
          break;
      }
    }
  }

  console.log("quartersYears:", quartersYears);
  console.log("RRarray:", RRarray);

  //Sales chart
  var ctx = document.getElementById("sales-chart");
  ctx.height = 150;
  var myChart = new Chart(ctx, {
    type: "line",
    data: {
      labels: quartersYears,
      type: "line",
      defaultFontFamily: "Montserrat",
      datasets: [
        {
          label: productSelected.name,
          data: RRarray,
          backgroundColor: "",
          borderColor: "#7571F9",
          borderWidth: 3,
          pointStyle: "circle",
          pointRadius: 5,
          pointBorderColor: "transparent",
          pointBackgroundColor: "#7571F9",
        },
      ],
    },
    options: {
      responsive: true,

      tooltips: {
        mode: "index",
        titleFontSize: 12,
        titleFontColor: "#000",
        bodyFontColor: "#000",
        backgroundColor: "#fff",
        titleFontFamily: "Montserrat",
        bodyFontFamily: "Montserrat",
        cornerRadius: 3,
        intersect: false,
      },
      legend: {
        labels: {
          usePointStyle: true,
          fontFamily: "Montserrat",
        },
      },
      scales: {
        xAxes: [
          {
            display: true,
            gridLines: {
              display: false,
              drawBorder: false,
            },
            scaleLabel: {
              display: false,
              labelString: "Month",
            },
          },
        ],
        yAxes: [
          {
            display: true,
            gridLines: {
              display: false,
              drawBorder: false,
            },
            scaleLabel: {
              display: true,
              labelString: "Value",
            },
          },
        ],
      },
      title: {
        display: false,
        text: "Normal Legend",
      },
    },
  });


}
