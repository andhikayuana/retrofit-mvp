package yuana.kodemetro.com.cargallery.features.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import yuana.kodemetro.com.cargallery.R;
import yuana.kodemetro.com.cargallery.models.Car;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/9/17
 */
public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {

    private Context mContext;
    private List<Car> mCars;

    public CarsAdapter(Context mContext, List<Car> mCars) {
        this.mContext = mContext;
        this.mCars = mCars;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_car, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        renderData(mCars.get(position), holder);
    }

    private void renderData(final Car car, ViewHolder holder) {
        holder.tvMake.setText(car.getMake());
        holder.tvModel.setText(car.getModel());
        holder.tvYear.setText(car.getYear());
        holder.llItemCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "halo " + car.getModel(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llItemCar;
        TextView tvMake;
        TextView tvModel;
        TextView tvYear;

        public ViewHolder(View itemView) {
            super(itemView);

            llItemCar = (LinearLayout) itemView.findViewById(R.id.llItemCar);
            tvMake = (TextView) itemView.findViewById(R.id.tvMake);
            tvModel = (TextView) itemView.findViewById(R.id.tvModel);
            tvYear = (TextView) itemView.findViewById(R.id.tvYear);

        }
    }
}
