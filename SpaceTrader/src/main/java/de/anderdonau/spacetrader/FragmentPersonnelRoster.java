/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;

@SuppressWarnings("ConstantConditions")
public class FragmentPersonnelRoster extends Fragment {
	View rootView;
	GameState gameState;

	public FragmentPersonnelRoster(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		int i;
		TableLayout tl;
		TextView tv;
		Button btn;
		rootView = inflater.inflate(R.layout.fragment_personnel_roster, container, false);

		for (i = 0; i < 2; ++i) {
			if ((gameState.JarekStatus == 1 || gameState.WildStatus == 1) && i < 2) {
				if (gameState.JarekStatus == 1 && i == 0) { /* Jarek is always in 1st crew slot */
					tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew1);
					tv = (TextView) rootView.findViewById(R.id.txtNameCrew1);
					btn = (Button) rootView.findViewById(R.id.btnFireCrew1);
					tl.setVisibility(View.INVISIBLE);
					tv.setText("Jarek's quarters");
					btn.setVisibility(View.INVISIBLE);
					continue;
				} else if (gameState.JarekStatus == 1 && gameState.WildStatus == 1 && i == 1) { /* Wild is in 2nd crew slot if Jarek is here, too */
					tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew2);
					tv = (TextView) rootView.findViewById(R.id.txtNameCrew2);
					btn = (Button) rootView.findViewById(R.id.btnFireCrew2);
					tl.setVisibility(View.INVISIBLE);
					tv.setText("Wild's quarters");
					btn.setVisibility(View.INVISIBLE);
					continue;
				} else if (gameState.WildStatus == 1 && i == 0) {/* Wild is in 1st crew slot if Jarek is not here */
					tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew1);
					tv = (TextView) rootView.findViewById(R.id.txtNameCrew1);
					btn = (Button) rootView.findViewById(R.id.btnFireCrew1);
					tl.setVisibility(View.INVISIBLE);
					tv.setText("Wild's quarters");
					btn.setVisibility(View.INVISIBLE);
					continue;
				}
				Log.e("PersonnelRoster", String.format(
					"Impossible Error: Jarek is %d, Wild is %d, here anyway...", gameState.JarekStatus,
					gameState.WildStatus
				)
				);
			}

			if (i == 0) {
				tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew1);
				tv = (TextView) rootView.findViewById(R.id.txtNameCrew1);
				btn = (Button) rootView.findViewById(R.id.btnFireCrew1);
			} else {
				tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew2);
				tv = (TextView) rootView.findViewById(R.id.txtNameCrew2);
				btn = (Button) rootView.findViewById(R.id.btnFireCrew2);
			}
			ShipTypes.ShipType Ship = gameState.ShipTypes.ShipTypes[gameState.Ship.type];
			if (Ship.crewQuarters <= i + 1) {
				tl.setVisibility(View.INVISIBLE);
				btn.setVisibility(View.INVISIBLE);
				tv.setText("No quarters available");
				continue;
			}

			if (gameState.Ship.crew[i + 1] < 0) {
				tl.setVisibility(View.INVISIBLE);
				btn.setVisibility(View.INVISIBLE);
				tv.setText("Vacancy");
				continue;
			}

			tl.setVisibility(View.VISIBLE);
			btn.setVisibility(View.VISIBLE);
			DrawMercenary(i, gameState.Ship.crew[i + 1]); /* Crew idx 0 is the player */
		}

		int ForHire = gameState.GetForHire();
		tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrewNew);
		tv = (TextView) rootView.findViewById(R.id.txtNameCrewNew);
		btn = (Button) rootView.findViewById(R.id.btnHireCrewNew);
		if (ForHire < 0) {
			tl.setVisibility(View.INVISIBLE);
			tv.setText("No one for hire");
			btn.setVisibility(View.INVISIBLE);
		} else {
			tl.setVisibility(View.VISIBLE);
			btn.setVisibility(View.VISIBLE);
			DrawMercenary(2, ForHire);
		}

		return rootView;
	}

	public void DrawMercenary(int i, int idxCrewMember) {
		TextView txtPilot;
		TextView txtEngineer;
		TextView txtTrader;
		TextView txtFighter;
		TextView txtName;

		CrewMember c = gameState.Mercenary[idxCrewMember];

		if (i == 0) {
			txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrew1);
			txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrew1);
			txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrew1);
			txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrew1);
			txtName = (TextView) rootView.findViewById(R.id.txtNameCrew1);
		} else if (i == 1) {
			txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrew2);
			txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrew2);
			txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrew2);
			txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrew2);
			txtName = (TextView) rootView.findViewById(R.id.txtNameCrew2);
		} else /* if (i == 2) */ {
			txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrewNew);
			txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrewNew);
			txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrewNew);
			txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrewNew);
			txtName = (TextView) rootView.findViewById(R.id.txtNameCrewNew);
		}
		txtPilot.setText(String.format("%d", c.pilot));
		txtFighter.setText(String.format("%d", c.fighter));
		txtEngineer.setText(String.format("%d", c.engineer));
		txtTrader.setText(String.format("%d", c.trader));
		txtName.setText(gameState.MercenaryName[c.nameIndex]);
	}
}
