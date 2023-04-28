package com.bwap.weatherapp.AirPollutionDetectionApp.view;

import com.bwap.weatherapp.AirPollutionDetectionApp.controller.AirQuality;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


@SpringUI(path = "")
public class MainView<cityName> extends UI {
    @Autowired
    private AirQuality airQuality;
    private VerticalLayout mainLayout;

    private TextField lat;
    private TextField lon;
    private Button searchButton;
    private Label location ;
    private Label currentTemp;
    private Label Description;
    private Label weatherMin;
    private Label weatherMax;
    private Label AQI;
    private Label AQIPred;
    private Label windSpeedLabel;
    private Label feelsLike;
    private Image iconImg;
    private HorizontalLayout Dashboard;
    private HorizontalLayout pollutionDescription;
    private Image logo;
    private HorizontalLayout footer;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setUpLayout();
        setHeader();
        setForm();
        dashboardTitle();
        dashboardDetails();

        footer();
        searchButton.addClickListener(clickEvent -> {
            if (!lat.getValue().equals("")&!lon.getValue().equals("")){
                try {
                    updateUI();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else
                Notification.show("Please Enter The cord");
        });


    }



    public void setUpLayout() {
        logo = new Image();
        iconImg = new Image();
        iconImg.setWidth("200px");
        iconImg.setHeight("200px");
        mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        mainLayout.setStyleName("BackColorGrey");
        setContent(mainLayout);
    }
    private void setHeader(){
        HorizontalLayout headerlayout = new HorizontalLayout();
        headerlayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        Label Title = new Label("Air pollution app");
        Title.addStyleName(ValoTheme.LABEL_H1);
        Title.addStyleName(ValoTheme.LABEL_BOLD);
        Title.addStyleName(ValoTheme.LABEL_COLORED);

        headerlayout.addComponents(Title);
        mainLayout.addComponents(headerlayout);


    }

    private void setForm(){
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        formLayout.setSpacing(true);
        formLayout.setMargin(true);


        //lat TextField
        lat = new TextField();
        lat.setWidth("20%");
        formLayout.addComponents(lat);

        lon = new TextField();
        lon.setWidth("80%");
        formLayout.addComponents(lat);

        //Search Icon
        searchButton = new Button();
        searchButton.setIcon(VaadinIcons.SEARCH);
        formLayout.addComponent(searchButton);

        lon = new TextField();
        lon.setWidth("80%");
        formLayout.addComponents(lat);
        formLayout.addComponents(lon);


        mainLayout.addComponents(formLayout);
    }
    private void dashboardTitle() {
        Dashboard = new HorizontalLayout();
        Dashboard.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);


        //City Location
        location = new Label("Currently in location with following lat: "+this.lat.getValue()+ " and lon");
        location.addStyleName(ValoTheme.LABEL_H2);
        location.addStyleName(ValoTheme.LABEL_LIGHT);




    }
    private void dashboardDetails(){
        pollutionDescription = new HorizontalLayout();
        pollutionDescription.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //descriptjon Layout
        VerticalLayout descriptionLayout = new VerticalLayout();
        descriptionLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //Weather Description  dummyData
        Description = new Label("Description: Clear Skies");
        Description.setStyleName(ValoTheme.LABEL_SUCCESS);
        descriptionLayout.addComponents(Description);

        //Min weather   dummyData
        weatherMin = new Label("Min:53");
        descriptionLayout.addComponents(weatherMin);
        //Max weather dummyData
        weatherMax = new Label("Max:22");
        descriptionLayout.addComponents(weatherMax);

        // Pressure, humidity, wind, Felslike dummyData

        VerticalLayout pressureLayout = new VerticalLayout();
        pressureLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        AQI = new Label("null");
        pressureLayout.addComponents(AQI);

        windSpeedLabel = new Label("124/hr");
        pressureLayout.addComponents(windSpeedLabel);

        feelsLike = new Label("FeelsLike:");
        pressureLayout.addComponents(feelsLike);




        pollutionDescription.addComponents(descriptionLayout, pressureLayout);

    }

    //footer

    private void footer(){
        footer = new HorizontalLayout();
        footer.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        footer.setSpacing(true);
        footer.setMargin(true);
        footer.setWidth("100%");
        footer.setHeight("40px");
        Label description = new Label();
        description.setValue("Air pollution");
        footer.addComponents(description);
        mainLayout.addComponents(footer);
    }


    //UI Update Method
    //Will be invoked only when Search Button is Clicked
    private void updateUI() throws JSONException {
        //Getting City name from text field and assignig it to API URL
        String lat = this.lat.getValue();
        String lon =this.lon.getValue();
        String defaultUnit;
        airQuality.setLat(lat);
        airQuality.setLon(lon);

        //Checking Units and Assigning value to API url using setUnit()



        // getting Icon form API
        String AQIValue = null;
        String components = null;
        JSONArray jsonArray = airQuality.returnHistoricalAnalysisArray();
        for (int i = 0; i< jsonArray.length(); i++){
            JSONObject weatherObject = jsonArray.getJSONObject(i);
            AQIValue = weatherObject.getString("main");
            components = weatherObject.getString("components");

        }
            AQI.setValue("AQI"+AQIValue);

        //updating Weather Description
        Description.setValue("Description: "+components);

//
//
//        //Updating Max Temp
//        weatherMax.setValue("Max Temp: "+ airQuality.returnMainObject().getInt("temp_max")+"\u00b0" +unitSelect.getValue());
//        //Updating Min Temp
//        weatherMin.setValue("Min Temp: "+ airQuality.returnMainObject().getInt("temp_min")+"\u00b0" +unitSelect.getValue());
//        //Updating Pressure
//        pressureLabel.setValue("Pressure: "+ airQuality.returnMainObject().getInt("pressure"));
//        //Updating Humidity
//        humidityLabel.setValue("Humidity: "+ airQuality.returnMainObject().getInt("humidity"));
//
//        //Updating Wind
//        windSpeedLabel.setValue("Wind: "+ airQuality.returnWindObject().getInt("speed")+"m/s");
//        //Updating Feels Like
//        feelsLike.setValue("Feelslike: "+ airQuality.returnMainObject().getDouble("feels_like"));
//
//




        //Adding Dashboard and main Description
        //it will only appear when the search button is clicked
        mainLayout.addComponents(Dashboard, pollutionDescription,footer);
    }







}