                       new AlertDialog.Builder(context)
                                            .setTitle("Confirmation")
                                            .setMessage("Do you really want to Delete This?")
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    DeleteThis  deleteThis = new DeleteThis();
                                                    deleteThis.execute(curent.getTitle());
                                                    SetFragment(new ChangeFragment());

                                                }})
                                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            }).show();
