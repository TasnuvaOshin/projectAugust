Store korben jokon : 


     SharedPreferences myPrefs = getActivity().getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("MEM1", jsonObject.getString("email"));
                editor.putString("MEM2", jsonObject.getString("user_name"));
                editor.commit();


                Picasso.get().load("http://search-me.xyz/searchme/img/" + jsonObject.getString("img_url")).fit().into(imageView);



Retrive korben Jokon : 



   SharedPreferences myPrefs = ProfileActivity.this.getSharedPreferences("myPrefs", MODE_PRIVATE);
                String myemail = myPrefs.getString("MEM1", "");
                String myname = myPrefs.getString("MEM2", "");
