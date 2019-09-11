189

step 1.to send data from fragment to activity

Intent intent = new Intent(getActivity().getBaseContext(),
                        TargetActivity.class);
                intent.putExtra("message", message);
                getActivity().startActivity(intent);
step 2.to receive this data in Activity:

Intent intent = getIntent();
String message = intent.getStringExtra("message");
step 3. to send data from activity to another activity follow normal approach

Intent intent = new Intent(MainActivity.this,
                        TargetActivity.class);
                intent.putExtra("message", message);
                startActivity(intent);
step 4 to receive this data in activity

     Intent intent = getIntent();
  String message = intent.getStringExtra("message");
Step 5. From Activity you can send data to Fragment with intent as:

Bundle bundle=new Bundle();
bundle.putString("message", "From Activity");
  //set Fragmentclass Arguments
Fragmentclass fragobj=new Fragmentclass();
fragobj.setArguments(bundle);
and to receive in fragment in Fragment onCreateView method:

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
          String strtext=getArguments().getString("message");

    return inflater.inflate(R.layout.fragment, container, false);
    }
